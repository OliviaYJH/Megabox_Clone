package com.example.rc_aos_megabox.naverapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NaverSearchInterface {
    @GET("search/{type}")
    fun getMovies(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientPw: String,
        @Path("type") type: String,
        @Query("query") query: String
    ): Call<MovieImageResponse>
}