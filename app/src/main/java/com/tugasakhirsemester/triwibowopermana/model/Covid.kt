package com.tugasakhirsemester.triwibowopermana.model

/**
 * Data class Covid yang digunakan untuk menampung data-data corresspond ke data covid-19.
 *
 * Ketika data class ini dipanggil / diinvoke terdapat 5 mandatory argument parameter
 * yang harus diberikan:
 * * 1. argument name berupa string value.
 * * 2. argument positif berupa string value.
 * * 3. argument sembuh berupa string value.
 * * 4. argument meninggal berupa string value.
 * * 5. argument dirawat berupa string value.
 */
data class Covid(
    val name: String,
    val positif: String,
    val sembuh: String,
    val meninggal: String,
    val dirawat: String
)