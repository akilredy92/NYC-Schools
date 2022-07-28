package com.nycschool.com.network

import com.nycschool.com.model.SATScores
import com.nycschool.com.model.Schools
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("resource/s3k6-pzi2.json")
    fun getAllSchools() : Call<List<Schools>>

    @GET("resource/f9bf-2cp4.json")
    fun getSATScores(@Query("dbn")dbn:String) :Call<List<SATScores>>

    companion object {

        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://data.cityofnewyork.us/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }


}