package com.example.rc_aos_megabox

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val sRetrofit = initRetrofit()
    private const val MOVIE_URL = "https://www.kobis.or.kr"

    private fun initRetrofit(): Retrofit =
        Retrofit.Builder()
                .baseUrl(MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
}