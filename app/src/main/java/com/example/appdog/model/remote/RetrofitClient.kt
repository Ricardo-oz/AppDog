package com.example.appdog.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {

        private const val BASE_URL = "https://dog.ceo/api/"

        lateinit var  retrofitInstance : Retrofit
        fun retrofitInstance(): DogsApi{
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()).build()
            return retrofit.create(DogsApi::class.java)
        }
    }
}