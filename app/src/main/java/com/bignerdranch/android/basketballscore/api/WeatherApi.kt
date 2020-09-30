package com.bignerdranch.android.basketballscore.api

import retrofit2.Call
import retrofit2.http.GET

interface WeatherApi {

    @GET("/")
    fun fetchContents(): Call<String>
}