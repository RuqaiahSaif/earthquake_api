package com.bignerdranch.android.earthquakesproject

import com.google.gson.annotations.SerializedName

data class EarthquakeItem (
        @SerializedName("properties")
        var property: Property = Property(),

        @SerializedName("geometry")
        var geometry: Geogeometry = Geogeometry()
    )
{}