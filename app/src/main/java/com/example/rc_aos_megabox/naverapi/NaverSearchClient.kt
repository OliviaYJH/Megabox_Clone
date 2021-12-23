package com.example.rc_aos_megabox.naverapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NaverSearchClient {
    val sRetrofit = initRetrofit()
    private const val BASE_URL = "https://openapi.naver.com/v1/"

    private fun initRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


}