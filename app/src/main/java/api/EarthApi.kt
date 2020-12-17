package api

import com.bignerdranch.android.earthquakesproject.EarthquakeResponse
import retrofit2.Call
import retrofit2.http.GET

interface EarthApi {
    @GET("query?format=geojson&limit=10")
    fun fetchContents(): Call<EarthquakeResponse>
}