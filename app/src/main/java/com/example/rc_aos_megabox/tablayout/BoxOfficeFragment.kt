package com.example.rc_aos_megabox.tablayout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.rc_aos_megabox.*
import com.example.rc_aos_megabox.databinding.ActivityMainBinding
import com.example.rc_aos_megabox.databinding.FragmentBoxOfficeBinding
import com.example.rc_aos_megabox.databinding.RecyclerviewItemBinding
import com.example.rc_aos_megabox.naverapi.NaverSearchClient
import com.example.rc_aos_megabox.naverapi.NaverSearchInterface
import com.example.rc_aos_megabox.tablayout.BoxOfficeFragment.Companion.imgList
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder


class BoxOfficeFragment : Fragment() {

    private var API_KEY = "b759981996e94214280b6ef4dc3fe99b"
    private lateinit var binding: FragmentBoxOfficeBinding

    lateinit var movieAdapter: MovieAdapter
    val datas = mutableListOf<MovieData>()

    var titleList = arrayOfNulls<String>(10)
    var rankList = arrayOfNulls<String>(10)
    var movieCd = arrayOfNulls<String>(10)
    var buyList = arrayListOf("74.3", "13.7", "4.9", "1.4", "1.4", "0.7", "0.7", "0.5", "0.4", "0.3")
    var starList = arrayListOf("9.4", "8.1", "7.8", "7.8", "8.8","9.7", "9.1", "9.2", "8.4", "8.4")
    var imgArray = arrayListOf(R.drawable.spiderman_poster, R.drawable.second, R.drawable.third,
        R.drawable.fourth, R.drawable.fifth, R.drawable.six, R.drawable.seven,
        R.drawable.eight, R.drawable.nine, R.drawable.ten)


    companion object{
        var imgList = arrayOfNulls<String>(10)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoxOfficeBinding.inflate(layoutInflater)

        getMovieData(API_KEY, "20211221")

        return binding.root
    }

    private fun getMovieData(key: String, date: String){
        val movieInterface = RetrofitClient.sRetrofit.create(RetrofitInterface::class.java)

        movieInterface.getMovie(key, date).enqueue(object: Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body() as MovieResponse
                    for(i in 0..9){
                        var text = result.boxOfficeResult.dailyBoxOfficeList.elementAt(i).movieNm
                        var rank = result.boxOfficeResult.dailyBoxOfficeList.elementAt(i).rank
                        var moviecd = result.boxOfficeResult.dailyBoxOfficeList.elementAt(i).movieCd
                        var movieImage = MovieImage()

                       val thread = Thread {
                           movieImage.main(text)
                       }.start()


                        if(text.count() >= 11){
                            text = text.substring(0,10) + "..."
                        }
                        titleList[i] = text
                        rankList[i] = rank
                        movieCd[i] = moviecd
                    }
                    initRecycler()

                }else{
                    Log.d("fail", "error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("onFailure", t.message ?: "통신오류")
            }
        })
    }

    private fun getMovieImage(query: String){
        val imgInterface = NaverSearchClient.sRetrofit.create(NaverSearchInterface::class.java)

    }

    private fun initRecycler(){


        movieAdapter = MovieAdapter(this)
        binding.rvMovie.adapter = movieAdapter


        datas.apply {
            for(i in 0..9){

                // add(MovieData( , ))
                titleList[i]?.let { rankList[i]?.let { it1 -> MovieData(title = it, rank = it1,
                    buy = buyList[i], star = starList[i], img = imgArray[i]) } }
                    ?.let { add(it) }

                /*
                titleList[i]?.let { rankList[i]?.let { it1 -> MovieData(title = it, rank = it1,
                                    buy = buyList[i], star = starList[i]), img = imgList[i]} }
                    ?.let { add(it) }
                 */
            }

            movieAdapter.datas = datas
            movieAdapter.notifyDataSetChanged()
        }


    }

}

class MovieImage {
    private val clientId = "0TiT2m5JCnaWgybu8eLH"
    private val clientSecret = "hUJZDFigbJ"
    private lateinit var binding: ActivityMainBinding

    fun main(text: String) {

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

    private fun parseData(responseBody: String) {
        var image: String
        var jsonObject: JSONObject?= null

        try{
            jsonObject = JSONObject(responseBody)
            val jsonArray = jsonObject?.getJSONArray("items")
            Log.d("jsonArray", jsonArray.length().toString())

            var j = 0
            for(i in 0 until jsonArray.length()){
                val item = jsonArray.getJSONObject(i)
                image = item.getString("image") // 해당 영화 포스터 url 가져오기

                BoxOfficeFragment.imgList[j] = image
                j += 1
            }

        }catch (e: JSONException){
            e.printStackTrace()
        }
    }
}