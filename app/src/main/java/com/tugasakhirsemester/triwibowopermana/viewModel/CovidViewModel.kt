package com.tugasakhirsemester.triwibowopermana.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tugasakhirsemester.triwibowopermana.model.Covid
import com.tugasakhirsemester.triwibowopermana.service.CovidApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CovidViewModel(application: Application) : AndroidViewModel(application) {
    private val _application: Application = application

    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val listCovid: MutableLiveData<List<Covid>> by lazy {
        MutableLiveData<List<Covid>>()
    }


    fun getData() {
        showLoading(true)
        CovidApiService.endPoint.getData()
            .enqueue(object: Callback<List<Covid>> {
                override fun onFailure(call: Call<List<Covid>>, t: Throwable) {
                    Log.e("getDataByCountry", "Failure", t)
                    showLoading(false)
                    showErrorMsg("Terjadi kesalahan saat mengakses API!")
                }

                override fun onResponse(
                    call: Call<List<Covid>>,
                    response: Response<List<Covid>>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        listCovid.value = response.body()
                    } else {
                        showErrorMsg("Terjadi kesalahan saat mengambil data!")
                    }
                }
            })
    }

    private fun showErrorMsg(message: String) {
        Toast.makeText(_application, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(loading: Boolean) {
        isLoading.value = loading
    }
}