package com.testsandroid.earthquake.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.testsandroid.earthquake.Constants
import com.testsandroid.earthquake.Earthquake
import com.testsandroid.earthquake.R
import com.testsandroid.earthquake.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    /*companion object{

    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val earthquake = intent?.extras?.getParcelable<Earthquake>(Constants.EQ_KEY)!!
        binding.magnitudeText.text = getString(R.string.magnitude_format, earthquake.magnitud)
        binding.longitudeText.text = earthquake.longitud.toString()
        binding.latitudeText.text = earthquake.latitud.toString()
        binding.placeText.text = earthquake.place
        setupTime(binding, earthquake)
    }
    private fun setupTime(binding: ActivityDetailBinding,
                          earthquake: Earthquake) {
        val dateFormat = SimpleDateFormat(Constants.TIME, Locale.getDefault())
        val date = Date(earthquake.time)
        binding.timeText.text = dateFormat.format(date)
    }
}