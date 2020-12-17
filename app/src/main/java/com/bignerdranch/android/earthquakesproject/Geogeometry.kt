package com.bignerdranch.android.earthquakesproject

import com.google.gson.annotations.SerializedName

data class Geogeometry (
    @SerializedName("coordinates")
    var geos: List<Double> = emptyList()
)
