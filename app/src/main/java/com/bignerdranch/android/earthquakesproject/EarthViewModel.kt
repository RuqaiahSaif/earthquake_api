package com.bignerdranch.android.earthquakesproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class EarthViewModel:ViewModel() {
    val earthquakeLiveData: LiveData<List<EarthquakeItem>>

    init {
        earthquakeLiveData = EarthquakeFetcher().feachData()
    }
}