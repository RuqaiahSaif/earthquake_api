package com.bignerdranch.android.earthquakesproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import api.EarthApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EarthquakeFetcher {
    lateinit var earthquakeApi: EarthApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/")
            .build()
        earthquakeApi = retrofit.create(EarthApi::class.java)
    }
    fun feachData(): MutableLiveData<List<EarthquakeItem>> {
        val responseLiveData: MutableLiveData<List<EarthquakeItem>> = MutableLiveData()
        val eathquakHomePageRequest: Call<EarthquakeResponse> = earthquakeApi.fetchContents()
        eathquakHomePageRequest.enqueue(object : Callback<EarthquakeResponse> {
            override fun onResponse(call: Call<EarthquakeResponse>, response: Response<EarthquakeResponse>) {
                var earthResponse = response.body()
                var eathquakes = earthResponse?.earth
                    ?: mutableListOf()

                responseLiveData.value = eathquakes
                Log.d("test", response.body().toString())
                Log.d("test", response.code().toString())

            }

            override fun onFailure(call: Call<EarthquakeResponse>, t: Throwable) {
                Log.d("test", t.message.toString())
            }

        })

        return responseLiveData

    }
}