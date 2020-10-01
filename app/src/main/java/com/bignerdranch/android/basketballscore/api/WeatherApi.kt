package com.bignerdranch.android.basketballscore.api

import retrofit2.Call
import retrofit2.http.GET

interface WeatherApi {

    @GET("https://api.openweathermap.org/data/2.5/weather?id=2633563&appid=e189973f9892cbce56309dedabd084a8")
    fun fetchWeather(): Call<OpenWeatherObject>
}
