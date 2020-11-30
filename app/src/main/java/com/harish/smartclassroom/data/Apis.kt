package com.harish.smartclassroom.data

import com.harish.smartclassroom.data.models.BatchResponse
import com.harish.smartclassroom.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface Apis {

    @GET("view_batch.php")
    fun getBatches(): Call<BatchResponse>


    companion object {
        operator fun invoke(): Apis {
            var logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            val client: OkHttpClient =
                    OkHttpClient.Builder().addInterceptor(logger).build()

            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(BASE_URL)
                    .build()

                    .create(Apis::class.java)
        }
    }

}