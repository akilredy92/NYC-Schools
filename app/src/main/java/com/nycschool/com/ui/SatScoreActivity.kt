package com.nycschool.com.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nycschool.com.R
import com.nycschool.com.databinding.ActivitySatscoreActivityBinding
import com.nycschool.com.network.MainRepository
import com.nycschool.com.network.RetrofitService
import com.nycschool.com.viewmodel.MainViewModel
import com.nycschool.com.viewmodel.MyViewModelFactory

class SatScoreActivity : AppCompatActivity() {
    lateinit var binding: ActivitySatscoreActivityBinding

    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySatscoreActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()
        val dbn = intent.getStringExtra("dbn")
        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)
        viewModel.satScores.observe(this,{

          if (it.size>0) {
              binding.tvSchoolname.text ="School name: ${it.get(0).schoolName .toString()}"
              binding.mathScore.text ="Math score: ${it.get(0).satMathAvgScore.toString()}"
              binding.readingScore.text ="Reading score: ${it.get(0).satCriticalReadingAvgScore.toString()}"
              binding.writingScore.text ="Writing score: ${it.get(0).satWritingAvgScore.toString()}"
          }
            else{
                binding.tvSchoolname.text = resources.getString(R.string.no_details)
          }

        })
        viewModel.errorMessage.observe(this, Observer {

        })
        dbn?.let { viewModel.getSATScores(it) }
    }

    fun setActionBar() {
        val actionbar = supportActionBar
        actionbar!!.title = "Score Details"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}