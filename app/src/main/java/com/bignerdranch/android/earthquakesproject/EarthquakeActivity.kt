package com.bignerdranch.android.earthquakesproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EarthquakeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earthquake)
        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, EarthquakeFragment.newInstance())
                .commit()
        }
    }
}