package com.tugasakhirsemester.triwibowopermana.service

import com.tugasakhirsemester.triwibowopermana.model.Covid
import retrofit2.Call
import retrofit2.http.GET

/**
 * Sebuah invocation method untuk mengirimkan request dan menerima response tersebut dari webserver.
 */
// Didalam interface ini berisi sebuah kontrak method yang harus dipenuhi yang digunakan untuk
// mengirimkan request ke end point webserver dan menerima response dari webserver tersebut,
// yang mana  implementasinya akan dibuat / diterapkan ketika retrofit.create() diinvoke dan
// memberikan interface ini sebagai argument pada create() method tersebut.
interface ICovidApiEndpoint  {
    // GET decorator dari retrofit2 package digunakan untuk memberikan end point dari full URL
    // ketika method yang diannotate dengan decorator ini dipanggil atau diinvoke. Sehingga request
    // yang akan dikirimkan menjadi https://example.com/indonesia.
    @GET("indonesia")
            /**
             * Mengirimkan HTTP GET Request ke webserver dan menerima HTTP Response dari request
             * yang dikirimkan
             *
             * @return Call interface dari retrofi2 bertipe List<Covid>
             */
    fun getData(): Call<List<Covid>>
}