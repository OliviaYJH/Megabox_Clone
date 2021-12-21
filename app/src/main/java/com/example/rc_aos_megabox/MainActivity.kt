package com.example.rc_aos_megabox;

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_aos_megabox.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.lang.RuntimeException
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private var API_KEY = "b759981996e94214280b6ef4dc3fe99b"


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.button.setOnClickListener {
            getMovieData(API_KEY, "20211219")
        }
        setContentView(binding.root)
    }

    private fun getMovieData(key: String, date: String){
        val movieInterface = RetrofitClient.sRetrofit.create(RetrofitInterface::class.java)

        movieInterface.getMovie(key, date).enqueue(object: Callback<MovieResponse>{
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body() as MovieResponse
                    Log.d("result", result.boxOfficeResult.dailyBoxOfficeList.elementAt(0).movieNm)
                    var text = result.boxOfficeResult.dailyBoxOfficeList.elementAt(0).movieNm
                    val thread = Thread {
                        var movieImage = MovieImage()
                        movieImage.main(text)
                    }.start()

                }else{
                    Log.d("fail", "error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("onFailure", t.message ?: "통신오류")
            }


        })

    }


}
