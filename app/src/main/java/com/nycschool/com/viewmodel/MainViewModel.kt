package com.nycschool.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nycschool.com.model.SATScores
import com.nycschool.com.model.Schools
import com.nycschool.com.network.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val schoolList = MutableLiveData<List<Schools>>()
    val satScores = MutableLiveData<List<SATScores>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllMovies() {

        val response = repository.getAllMovies()
        response.enqueue(object : Callback<List<Schools>> {
            override fun onResponse(call: Call<List<Schools>>, response: Response<List<Schools>>) {
                schoolList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Schools>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getSATScores(dbn:String) {
        val response = repository.getSATScores(dbn)
        response.enqueue(object : Callback<List<SATScores>> {
            override fun onResponse(call: Call<List<SATScores>>, response: Response<List<SATScores>>) {
                satScores.postValue(response.body())
            }

            override fun onFailure(call: Call<List<SATScores>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}