package com.example.rc_aos_megabox.movieInfo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInfoInterface {

    @GET("/kobisopenapi/webservice/rest/movie/searchMovieInfo.json")

    fun getMovieInfo(@Query("key") key: String,
                    @Query("movieCd") movieCd: String
    ): Call<MovieInfoResponse>
}