package com.nycschool.com.network

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllMovies() = retrofitService.getAllSchools()
    fun getSATScores(dbn:String) = retrofitService.getSATScores(dbn)
}