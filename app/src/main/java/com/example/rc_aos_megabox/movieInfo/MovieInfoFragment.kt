package com.example.rc_aos_megabox.movieInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rc_aos_megabox.MovieResponse
import com.example.rc_aos_megabox.R
import com.example.rc_aos_megabox.RetrofitClient
import com.example.rc_aos_megabox.RetrofitInterface
import com.example.rc_aos_megabox.databinding.FragmentMovieInfoBinding
import com.example.rc_aos_megabox.tablayout.MovieImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieInfoFragment : Fragment() {

    private lateinit var binding: FragmentMovieInfoBinding
    private var API_KEY = "b759981996e94214280b6ef4dc3fe99b"
    private var movieCd: String?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieInfoBinding.inflate(layoutInflater)

        getMovieData(API_KEY, "20211221")

        arguments?.let {
            movieCd = it.getString("movieCd")
            //Toast.makeText(activity, it.getString("movieCd"), Toast.LENGTH_SHORT).show()
            movieCd?.let { getMovieInfoData(API_KEY, it) }
        }

        return binding.root
    }

    private fun getMovieInfoData(key: String,movieCd: String){
        val MovieInfoInterface = MovieInfoRetrofitClient.sRetrofit.create(MovieInfoInterface::class.java)

        MovieInfoInterface.getMovieInfo(key, movieCd).enqueue(object: retrofit2.Callback<MovieInfoResponse>{
            override fun onResponse(
                call: Call<MovieInfoResponse>,
                response: Response<MovieInfoResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body() as MovieInfoResponse
                    if(!result.movieInfoResult.movieInfo.openDt.equals("")){
                        var opendate = result.movieInfoResult.movieInfo.openDt
                        binding.tvMovieOpen.text = opendate.substring(0,4) + ". " + opendate.substring(4,6)  + ". " + opendate.substring(6,8)
                    }

                    var type = result.movieInfoResult.movieInfo.showTypes
                    var movietype = ""
                    //Log.d("size", type.get(1).showTypeGroupNm)

                    for(j in 0..type.size-1){
                        movietype += type.get(j).showTypeGroupNm
                        if(j != type.size-1){
                            movietype += ", "
                        }
                    }
                    binding.tvType.text = movietype

                    var genre = result.movieInfoResult.movieInfo.genres
                    var moviegenre = ""
                    for(j in 0..genre.size-1){
                        moviegenre += genre.get(j).genreNm
                        if(j != genre.size-1){
                            moviegenre += ", "
                        }
                    }
                    binding.tvGenre.text = moviegenre

                    binding.tvTime.text = result.movieInfoResult.movieInfo.showTm + " 분"

                    var director = result.movieInfoResult.movieInfo.directors
                    var moviedirector = ""
                    for(j in 0..director.size-1){
                        moviedirector += director.get(j).peopleNm
                        if(j != director.size-1){
                            moviedirector += ", "
                        }
                    }
                    binding.tvDirector.text = moviedirector

                    var actor = result.movieInfoResult.movieInfo.actors
                    var movieactor = ""
                    for(j in 0..actor.size-1){
                        movieactor += actor.get(j).peopleNm
                        if(j != actor.size-1){
                            movieactor += ", "
                        }
                    }
                    binding.tvActor.text = movieactor


                }else{
                    Log.d("fail", "error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieInfoResponse>, t: Throwable) {
                Log.d("onFailure", t.message ?: "통신오류")
            }

        })
    }

    private fun getMovieData(key: String, date: String){
        val movieInterface = RetrofitClient.sRetrofit.create(RetrofitInterface::class.java)

        movieInterface.getMovie(key, date).enqueue(object: Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body() as MovieResponse


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