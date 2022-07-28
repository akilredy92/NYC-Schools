package com.nycschool.com.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nycschool.com.databinding.ActivityMainBinding
import com.nycschool.com.model.SATScores
import com.nycschool.com.model.Schools
import com.nycschool.com.network.MainRepository
import com.nycschool.com.network.RetrofitService
import com.nycschool.com.viewmodel.MainViewModel
import com.nycschool.com.viewmodel.MyViewModelFactory

class MainActivity : AppCompatActivity(), MainAdapter.OnItemClickListener {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()
    val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)
        setActionBar()

        if(isOnline(this)) {
            binding.noText.visibility = View.GONE
            binding.recyclerview.adapter = adapter
            displayProgress(visible = true)
            viewModel.schoolList.observe(this, Observer {
                Log.d(TAG, "onCreate: $it")
                adapter.setSchoolList(it,this)
                displayProgress(visible = false)
            })

            viewModel.errorMessage.observe(this, Observer {

            })
            viewModel.getAllMovies()
        }
        else {
            binding.noText.visibility = View.VISIBLE
            Toast.makeText(this,"No internet connection available", Toast.LENGTH_LONG).show()
            displayProgress(visible = false)
        }
    }

    override fun onItemClick(item: Schools?) {
        val intent = Intent(this, SatScoreActivity::class.java)
        intent.putExtra("dbn", item?.dbn)
        startActivity(intent)
    }


    private fun displayProgress(visible: Boolean) {
        if (visible) showProgress() else hideProgress()
    }


    private fun hideProgress() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgress() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        binding.progressBar.visibility = View.VISIBLE
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun setActionBar() {
        val actionbar = supportActionBar
        actionbar!!.title = "NYC Schools"
    }

}
