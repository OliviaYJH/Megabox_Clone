package com.example.rc_aos_megabox

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")

    fun getMovie(@Query("key") key: String,
                @Query("targetDt") targetDt: String
    ): Call<MovieResponse>
}