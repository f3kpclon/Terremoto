package com.testsandroid.earthquake.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.testsandroid.earthquake.Constants
import com.testsandroid.earthquake.Earthquake
import com.testsandroid.earthquake.R
import com.testsandroid.earthquake.api.ApiResponseStatus
import com.testsandroid.earthquake.api.WorkerUtil
import com.testsandroid.earthquake.databinding.ActivityMainBinding
import com.testsandroid.earthquake.detail.DetailActivity

class MainActivity : AppCompatActivity() {
    private lateinit var viewMail: MainViewMail
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.eqRecycler.layoutManager = LinearLayoutManager(this)

        WorkerUtil.scheduleSync(this)
        val  sortType = getSortType()


        viewMail = ViewModelProvider(this, MainViewModelFactory(application,sortType)).get((MainViewMail::class.java))
        val eqAdapter = EqAdapter(this)
        binding.eqRecycler.adapter = eqAdapter

        eqAdapter.onItemClickListener = {
            openDetailActivity(it)
        }

        viewMail.eqList.observe(this, Observer {
            eqAdapter.submitList(it)
            if (it.size == 0) {
                binding.eqRecycler.visibility = View.GONE
                binding.eqEmptyView.visibility = View.VISIBLE
            } else {
                binding.eqRecycler.visibility = View.VISIBLE
                binding.eqEmptyView.visibility = View.GONE
            }
        })
        viewMail.status.observe(this, Observer {
            if (it == ApiResponseStatus.LOADING) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

            if (it == ApiResponseStatus.NOT_INTERNET_CONNECTION) {
                Toast.makeText(this, R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getSortType(): Boolean {
        val prefs = getPreferences(Context.MODE_PRIVATE)
        return  prefs.getBoolean(Constants.SORT_TYPE,false)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.by_magni_id){
            viewMail.reloadEarthquakeFromDB(true)
            saveSortType(true)

        }else if(itemId == R.id.by_time_id){
            viewMail.reloadEarthquakeFromDB(false)
            saveSortType(false)


        }
        return super.onOptionsItemSelected(item)
    }
    private fun openDetailActivity(earthquake: Earthquake) {
        val intent = Intent(this, DetailActivity::class.java)
        //se coloca el key con el que se identifica el value y el value
        intent.putExtra(Constants.EQ_KEY, earthquake)
        startActivity(intent)
    }
    private fun saveSortType(sortByMagnitud: Boolean){
        val prefs = getSharedPreferences("EQ_PREFS", Context.MODE_PRIVATE)
        val editor =  prefs.edit()
        editor.putBoolean(Constants.SORT_TYPE,sortByMagnitud)
        editor.apply()
    }


}