package com.testsandroid.earthquake.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.testsandroid.earthquake.Earthquake
import com.testsandroid.earthquake.R
import com.testsandroid.earthquake.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EQ_KEY = "earthquake"
        private const val TIME = "dd/MMM/yyyy HH:mm:ss"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val earthquake = intent?.extras?.getParcelable<Earthquake>(EQ_KEY)!!
        binding.magnitudeText.text = getString(R.string.magnitude_format, earthquake.magnitud)
        binding.longitudeText.text = earthquake.longitud.toString()
        binding.latitudeText.text = earthquake.latitud.toString()
        binding.placeText.text = earthquake.place
        setupTime(binding, earthquake)
    }
    private fun setupTime(binding: ActivityDetailBinding,
                          earthquake: Earthquake) {
        val dateFormat = SimpleDateFormat(TIME, Locale.getDefault())
        val date = Date(earthquake.time)
        binding.timeText.text = dateFormat.format(date)
    }
}