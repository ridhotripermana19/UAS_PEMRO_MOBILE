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

/**
 * Menyimpan dan mengelola data terkait UI dengan cara yang berbasis siklus proses untuk data covid.
 *
 * Class ViewModel CovidViewModel ini merupakan turunan (extends) dari class AndroidViewModel.
 * class ini juga memiliki 1 mandatory parameter yaitu application object ketika class ini
 * diinstansiasi.
 */
// Untuk ViewModel ini sendiri menurunkan class AndroidViewModel dikarenakan salah satu method
// didalam ViewModel ini memerlukan application context untuk menampilkan text menggunakan Toast
// object yang mana method makeText dari Toast object salah satu parameternya memerlukan application
// context untuk membuat text yang akan ditampilkan ke UI.
class CovidViewModel(application: Application) : AndroidViewModel(application) {
    // Assign private value _application object bertipe Application
    // dengan nilai application yang didapatkan dari constructor parameter
    // View Model CovidViewModel.
    private val _application: Application = application

    // Inisialisasi MutableLiveData bertipe boolean dengan nama isLoading
    // secara lazy. pada lamba function dari lazy ini akan menginstansiasi
    // MutableLiveData class bertipe boolean, yang akan diinvoke ketika onCreate() method
    // dari aplikasi dieksekusi.
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    // Inisialisasi MutableLiveData bertipe List<Covid> dengan nama listCovid
    // secara lazy. pada lamba function dari lazy ini akan menginstansiasi
    // MutableLiveData class bertipe List<Covid>, yang akan diinvoke ketika onCreate() method
    // dari aplikasi dieksekusi.
    val listCovid: MutableLiveData<List<Covid>> by lazy {
        MutableLiveData<List<Covid>>()
    }

    /**
     * Mengambil data dari Web Service / REST API.
     *
     */
    fun getData() {
        // Eksekusi private method showLoading dengan memberikan argument true pada parameter method
        // Untuk menampilkan progress bar di view, yang akan mengindikasikan bahwa suatu proses
        // sedang berlangsung kepada user.
        showLoading(true)

        // Memanggil method getData dari object endPoint yang diaman object endPoint tersebut
        // berada didalam object CovidApiService.
        //
        // Method getData() sendiri mengembalikan sebuah Call interface dari Retrofit
        // bertipe List<Covid>.
        //
        // Call pada retrofit adalah sebuah invocation dari Retrofit method yang akan mengirimkan
        // request kepada webserver dan mengembalikan response dari request yang dikirimkan.
        //
        // Untuk Call sendiri pada implementasi dibawah ini akan dijalankan secara asynchronous
        // dengan memanggil enqueue method dari Call interface. enqueue sendiri menerima Callback
        // onResponse yang akan diinvoke untuk HTTP Response yang diterima dari webserver
        // atau onFailure yang akan diinvoke ketika terjadi network exception seperti no internet
        // connection ketika menghubungkan ke webserver.
        CovidApiService.endPoint.getData()
            .enqueue(object: Callback<List<Covid>> {
                // onFailure akan diinvoke ketika terjadi network exception.
                override fun onFailure(call: Call<List<Covid>>, t: Throwable) {
                    // Log pesan error ke console, dimana pesan error didapatkan
                    // dari Throwable object
                    Log.e("getDataByCountry", "Failure", t)

                    // Eksekusi private method showLoading dengan memberikan argument false pada
                    // parameter method Untuk menampilkan progress bar di view, yang akan
                    // mengindikasikan bahwa suatu proses telah selesai kepada user.
                    showLoading(false)

                    // Memanggil private method showErrorMsg yang akan menampilkan text
                    // "Terjadi kesalahan saat mengakses API!" kepada user, ketika terjadi
                    // network exception.
                    showErrorMsg("Terjadi kesalahan saat mengakses API!")
                }

                // onResponse akan diinvoke ketika request yang dikirimkan ke webserver sukses
                // dan webserver mengembalikan response tersebut.
                override fun onResponse(
                    call: Call<List<Covid>>,
                    response: Response<List<Covid>>
                ) {
                    // Eksekusi private method showLoading dengan memberikan argument false pada
                    // parameter method Untuk menampilkan progress bar di view, yang akan
                    // mengindikasikan bahwa suatu proses telah selesai kepada user.
                    showLoading(false)

                    // Check jika response success.
                    // maka set value dari MutableLiveData listCovid dengan value yang berasal
                    // dari response body yang sebelumnya telah di parse dari JSON menjadi
                    // java object menggunakan GSON Convert Factory dari retrofit2 pacakge.
                    if (response.isSuccessful) {
                        listCovid.value = response.body()
                        // Jika response bukan merupakan success response
                    } else {
                        // Memanggil private method showErrorMsg yang akan menampilkan text
                        // "Terjadi kesalahan saat mengambil data!" kepada user, ketika HTTP CODE
                        // dari response bukan merupakan 200 sampai 300 status code.
                        showErrorMsg("Terjadi kesalahan saat mengambil data!")
                    }
                }
            })
    }

    /**
     * Menampilkan pesan error.
     * @param message - Pesan error yang akan ditampilkan.
     *
     */
    private fun showErrorMsg(message: String) {
        // Membuat text menggunakan makeText() method dari object Toast.
        // Metode makeText() memerlukan tiga parameter:
        //      * Aplikasi Context - Context yang akan digunakan ketika menampilkan pesan.
        //      * Pesan text - Isi pesan yang akan ditampilkan.
        //      * Durasi - Berapa lama pesan akan ditampilkan.
        // Kemudian untuk menampilkan text yang telah dibuat tersebut akan memanggil show() method
        // untuk menampilkan text yang telah dibuat dari object Toast.
        Toast.makeText(_application, message, Toast.LENGTH_SHORT).show()
    }


    /**
     * Loading indicator untuk progress bar.
     *
     * Jika loading parameter yang diberikan true, maka progress bar pada view akan ditampilkan,
     * sebaliknya jika false, maka progress bar pada view akan disembunyikan.
     *
     * @param loading - loading indicator berupa boolean value.*
     *
     */
    private fun showLoading(loading: Boolean) {
        // Set value dari MutableLiveData isLoading bertipe boolean
        // dengan nilai yang diberikan pada parameter method showLoading().
        isLoading.value = loading
    }
}