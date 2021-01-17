package com.tugasakhirsemester.triwibowopermana

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tugasakhirsemester.triwibowopermana.viewModel.CovidViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activiy yang akan dijalankan pertama kali oleh aplikasi.
 */
class MainActivity : AppCompatActivity() {
    // inisialisasi ViewModel CovidViewModel secara lazy yang mana didalam lamba function lazy
    // akan menginvoke instansiasi CovidViewModel ketika onCreate() method dieksekusi. untuk
    // instansiasi CovidViewModel akan diberikan application object sebagai argument parameter
    // pada constructor dari CovidViewModel.
    private val covidViewModel: CovidViewModel by lazy {
        CovidViewModel(application)
    }

    // Dijalankan ketika activity dimuat.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set Content View dari activity untuk menggunakan layout activity_main.xml
        setContentView(R.layout.activity_main)
    }

    // Dijalankan ketika onCreate method selesai dieksekusi atau activity mulai terlihat oleh user.
    override fun onStart() {
        super.onStart()
        // Menginvoke private method fetchDataCovid()
        fetchDataCovid()
        // Menginvoke private method onloadingProgress()
        onloadingProgress()
        // Menginvoke private method bindDataToView()
        bindDataToView()
    }

    /**
     * Menginvoke method dari ViewModel untuk memfetch data dari Web Service / REST API.
     */
    private fun fetchDataCovid() {
        // Menginvoke getData() method dari CovidViewModel.
        covidViewModel.getData()
    }

    /**
     * Mengikat data - data yang didapat kedalam view
     */
    private fun bindDataToView() {
        // Menginvoke listCovid LiveData yang ada didalam CovidViewModel yang kemudian akan
        // akan diobserve valuenya menggunakan method observe dari LiveData yang memerlukan 2
        // parameter owner yang merupakan lifecycle owner yang mengontrol observer, dan observer
        // yang bertugas untuk menerima event.
        covidViewModel.listCovid.observe(this, Observer {
            // Set text dari textview dengan id tv_cases dengan value positif dari List<Covid>
            // pada element pertama.
            tv_cases.text = it[0].positif

            // Set text dari textview dengan id tv_deaths dengan value meninggal dari List<Covid>
            // pada element pertama.
            tv_deaths.text = it[0].meninggal

            // Set text dari textview dengan id tv_recovered dengan value sembuh dari List<Covid>
            // pada element pertama.
            tv_recovered.text = it[0].sembuh
        })
    }

    /**
     * Menampilkan atau menghide progress bar.
     */
    private fun onloadingProgress() {
        // Menginvoke isLoading LiveData yang ada didalam CovidViewModel yang kemudian akan
        // akan diobserve valuenya menggunakan method observe dari LiveData yang memerlukan 2
        // parameter owner yang merupakan lifecycle owner yang mengontrol observer, dan observer
        // yang bertugas untuk menerima event.
        covidViewModel.isLoading.observe(this, Observer {
            // ketika nilai dari isLoading yang diobserve bernilai true maka progress bar akan
            // ditampilkan sedangkan untuk seluruh card view akan dihilangkan / dihide.
            //
            // sedangkan ketika nilai dari isLoading yang diobserve bernilai false maka progress bar
            // akan dihilangkan / hide dan untuk seluruh card view akan ditampilkan.
            when (it) {
                true -> {
                    pgBar.visibility = View.VISIBLE
                    cv_cases.visibility = View.GONE
                    cv_deaths.visibility = View.GONE
                    cv_recovered.visibility = View.GONE
                }

                false -> {
                    pgBar.visibility = View.GONE
                    cv_cases.visibility = View.VISIBLE
                    cv_deaths.visibility = View.VISIBLE
                    cv_recovered.visibility = View.VISIBLE
                }

            }
        })
    }
}