package com.example.rc_aos_megabox

import android.util.Log
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import retrofit2.http.GET
import java.io.*
import java.lang.RuntimeException
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import kotlin.coroutines.coroutineContext

class MovieImage {
    private val clientId = "0TiT2m5JCnaWgybu8eLH"
    private val clientSecret = "hUJZDFigbJ"

    fun main(text: String){
        var searchMovieNm: String?= null
        try{
            searchMovieNm = URLEncoder.encode(text, "UTF-8")
        }catch (e: UnsupportedEncodingException){
            throw RuntimeException("검색어 인코딩 실패", e)
        }

        val apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text!!

        val requestHeaders: HashMap<String, String> = HashMap()
        requestHeaders["X-Naver-Client-Id"] = clientId
        requestHeaders["X-Naver-Client-Secret"] = clientSecret
        val responseBody = get(apiURL, requestHeaders)

        parseData(responseBody)
    }

    private fun get(apiUrl: String, requestHeaders: Map<String, String>): String{
        val con = connect(apiUrl)
        try{
            con.requestMethod = "GET"
            for((key, value) in requestHeaders){
                con.setRequestProperty(key, value)
            }

            val responseCode = con.responseCode

            return if(responseCode == HttpURLConnection.HTTP_OK){
                readBody(con.inputStream)
            }else{
                readBody(con.errorStream)
            }
        }catch (e: IOException){
            throw RuntimeException("API 요청과 응답 실패", e)
        }finally {
            con.disconnect()
        }
    }

    private fun connect(apiUrl: String): HttpURLConnection {
        try{
            val url = URL(apiUrl)
            return url.openConnection() as HttpURLConnection
        }catch (e: MalformedURLException){
            throw RuntimeException("API URL이 잘못되었습니다 : $apiUrl", e)
        }catch (e: IOException){
            throw RuntimeException("연결이 실패했습니다 : $apiUrl", e)
        }
    }

    private fun readBody(body: InputStream): String{
        val streamReader = InputStreamReader(body)
        try{
            BufferedReader(streamReader).use { lineReader ->
                val responseBody = StringBuilder()

                var line: String? = lineReader.readLine()
                while (line != null) {
                    responseBody.append(line)
                    line = lineReader.readLine()
                }
                return responseBody.toString()
            }
        }catch (e: IOException){
            throw RuntimeException("API 응답을 읽는데 실패했습니다", e)
        }
    }

    private fun parseData(responseBody: String){
        var title: String
        var jsonObject: JSONObject?= null

        try{
            jsonObject = JSONObject(responseBody)
            val jsonArray = jsonObject?.getJSONArray("items")

            for(i in 0 until jsonArray.length()){
                val item = jsonArray.getJSONObject(i)
                title = item.getString("image") // 해당 영화 포스터 url 가져오기
                Log.d("TITLE", title)
            }
        }catch (e: JSONException){
            e.printStackTrace()
        }
    }
}