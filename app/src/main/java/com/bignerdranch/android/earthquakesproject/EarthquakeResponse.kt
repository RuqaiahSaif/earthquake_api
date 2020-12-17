package com.bignerdranch.android.earthquakesproject

import com.google.gson.annotations.SerializedName

data class EarthquakeResponse (
    @SerializedName("features")
    var earth: List<EarthquakeItem>
)