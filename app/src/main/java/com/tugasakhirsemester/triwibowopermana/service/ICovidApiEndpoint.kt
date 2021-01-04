package com.tugasakhirsemester.triwibowopermana.service

import com.tugasakhirsemester.triwibowopermana.model.Covid
import retrofit2.Call
import retrofit2.http.GET

interface ICovidApiEndpoint  {
    @GET("indonesia")
    fun getData(): Call<List<Covid>>
}