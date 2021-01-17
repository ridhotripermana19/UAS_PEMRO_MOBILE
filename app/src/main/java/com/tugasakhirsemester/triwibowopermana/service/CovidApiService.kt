package com.tugasakhirsemester.triwibowopermana.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CovidApiService {

    /**
     * Base URL dari Web Service / REST API
     */
    private const val BASE_URL: String = "https://api.kawalcorona.com/"

    // Membuat getter method dari ICovidApiEndpoint interface yang akan diinvoke ketika val endPoint
    // ini dipanggil.
    val endPoint: ICovidApiEndpoint
        get() {
            // Instansiasi HttpLoggingInterceptor dari okhttp3 package, yang berfungsi untuk menlog
            // response yang diterima dari webservice / REST API
            val interceptor = HttpLoggingInterceptor()

            // Set level dari logging interceptor yang hanya akan men-log body dari response.
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            // Build okhttpclient dari okhttp3 package dengan menambahkan loggingInterceptor.
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            // Build retrofit service dengan baseUrl berdasarkan const val BASE_URL kemudian
            // menambahkan OkHttpClient yang object yang terdapat pada val client, kemudian
            // menambahkan convert factory GsonConverterFactory dari retrofit2 package, untuk
            // memparsing response berupa JSON menjadi java object.
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Mengenerate implementation dari ICovidApiEndpoint interface menggunakan
            // create method dari Retrofit object.
            return retrofit.create(ICovidApiEndpoint::class.java)
        }
}