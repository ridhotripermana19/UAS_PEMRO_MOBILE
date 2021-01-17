package com.tugasakhirsemester.triwibowopermana

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tugasakhirsemester.triwibowopermana.viewModel.CovidViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val covidViewModel: CovidViewModel by lazy {
        CovidViewModel(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        fetchDataCovid()
        onloadingProgress()
        bindDataToView()
    }

    private fun fetchDataCovid() {
        covidViewModel.getData()
    }

    private fun bindDataToView() {
        covidViewModel.listCovid.observe(this, Observer {
            tv_cases.text = it[0].positif
            tv_deaths.text = it[0].meninggal
            tv_recovered.text = it[0].sembuh
        })
    }

    private fun onloadingProgress() {
        covidViewModel.isLoading.observe(this, Observer {
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