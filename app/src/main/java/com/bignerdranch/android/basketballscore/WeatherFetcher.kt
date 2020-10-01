package com.bignerdranch.android.basketballscore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.basketballscore.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG_ = "WeatherFetcher"

class WeatherFetcher {

    private val weatherApi: WeatherApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    fun fetchWeather(): LiveData<Pair<MutableList<String>?, Double?>> {
        val responseLiveData: MutableLiveData<Pair<MutableList<String>?, Double?>> = MutableLiveData()
        val weatherRequest: Call<OpenWeatherObject> = weatherApi.fetchWeather()

        weatherRequest.enqueue(object: Callback<OpenWeatherObject> {

            override fun onFailure(call: Call<OpenWeatherObject>, t: Throwable) {
                Log.e(TAG_, "Failed to fetch weather data", t)
            }

            override fun onResponse (
                call: Call<OpenWeatherObject>,
                response: Response<OpenWeatherObject>
            ) {
                Log.d(TAG_, "Response recieved")
                // Get full JSON response object
                val openWeatherObject: OpenWeatherObject? = response.body()

                // Get sub-objects
                val weatherObject: List<WeatherObject>? = openWeatherObject?.weather
                val mainObject: MainObject? = openWeatherObject?.main

                // Get sub-object data
                val temp: Double? = mainObject?.temp
                val main: MutableList<String>? = mutableListOf<String>()
                if (weatherObject != null) {
                    for (obj in weatherObject) {
                        main!!.add(obj.main)
                    }
                }
                responseLiveData.value = Pair(main, temp)
            }
        })
        return responseLiveData
    }
}
