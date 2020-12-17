package com.bignerdranch.android.earthquakesproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode
import java.util.*


class EarthquakeFragment : Fragment() {
    private lateinit var earthViewModel: EarthViewModel
    private lateinit var earthquakeRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        earthViewModel = ViewModelProviders.of(this).get(EarthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_earthquake, container, false)
        earthquakeRecyclerView = view.findViewById(R.id.earth_recycler_view)
        earthquakeRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var earth = EarthquakeFetcher().feachData().observe(this, Observer {
            Log.d("test", "Response received: ${it}")
            earthquakeRecyclerView.adapter = EarthquakeAdapter(it)
        })
    }

    companion object {
        fun newInstance():EarthquakeFragment{
          return  EarthquakeFragment()
        }

    }
    private inner class EarthquakeHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val magButton = view.findViewById(R.id.Earthquake_digree) as Button
        val titleTextView = view.findViewById(R.id.title) as TextView
        val destanceTextView = view.findViewById(R.id.distance) as TextView
        val dateTextView = view.findViewById(R.id.date) as TextView
        val timeTextView = view.findViewById(R.id.time) as TextView
        private var longtude: Double = 0.0
        private var latitude: Double = 0.0

        init {
            view.setOnClickListener(this)
        }

        var earthquake = EarthquakeItem()
        fun bind(earthquake: EarthquakeItem) {
            this.earthquake = earthquake
            setMagButtonShape(this.earthquake.property.mag)
            if (this.earthquake.property.title.contains(" of ".toRegex())) {
                destanceTextView.text = this.earthquake.property.title.split("of")[0] + "of"
                titleTextView.text = this.earthquake.property.title.split("of")[1]
            } else {
                titleTextView.text = this.earthquake.property.title
                destanceTextView.text = this.earthquake.property.place

            }
            // titleTextView.text=this.earthquake.pro.title

            dateTextView.text = converToDate(earthquake.property.time)
            timeTextView.text = converToTime(earthquake.property.time)
            setCoordinates(this.earthquake.geometry.geos)
        }

        fun setMagButtonShape(mag: Double) {
            magButton.apply {
                text = mag.toBigDecimal().setScale(1, RoundingMode.CEILING).toString()
                when {
                    mag < 4.0 -> setBackgroundResource(R.drawable.shape1)
                    mag < 5.0 -> setBackgroundResource(R.drawable.shape2)
                    mag <= 6.0 -> setBackgroundResource(R.drawable.shape3)
                    mag in 6.0..10.0 -> setBackgroundResource(R.drawable.shape4)
                }
            }
        }

        fun converToDate(dateTime: Long): String {
            val calendar = Calendar.getInstance()

            calendar.time = Date(dateTime)

            val earthquakeDate: String = "${calendar.get(Calendar.YEAR)}-" +
                    "${calendar.get(Calendar.MONTH)}-" +
                    "${calendar.get(Calendar.DAY_OF_MONTH)}"

            return earthquakeDate
        }

        fun converToTime(dateTime: Long): String {
            val calendar = Calendar.getInstance()

            calendar.time = Date(dateTime)

            val earthquakeTime: String = "${calendar.get(Calendar.HOUR_OF_DAY)}:" +
                    "${calendar.get(Calendar.MINUTE)}"

            return earthquakeTime
        }

        fun setCoordinates(coordinates: List<Double>) {
            longtude = coordinates[0]
            latitude = coordinates[1]

        }

        override fun onClick(v: View?) {
            val uri = Uri.parse("geo:$latitude,$longtude")
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = uri
            }
            requireActivity().startActivity(intent)

        }


    }
    inner class EarthquakeAdapter(var earthquakes: List<EarthquakeItem>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view: View
            view = layoutInflater.inflate(
                R.layout.earthquake_item,
                parent, false
            )
            return EarthquakeHolder(view)
        }
        override fun getItemCount(): Int {

            return earthquakes.size
        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val earthquake = earthquakes[position]
            if (holder is EarthquakeHolder)
                holder.bind(earthquake)
        }
    }

}