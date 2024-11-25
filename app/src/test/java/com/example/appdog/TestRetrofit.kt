package com.example.appdog

import com.example.appdog.model.remote.RetrofitClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestRetrofit {
    private lateinit var mWS : MockWebServer

    @Before
    fun setup(){
        mWS= MockWebServer()

    }

    @After
    fun tearDown(){
        mWS.shutdown()
    }

    @Test
    fun testRetrofit() {

        val expectedBaseUrl = mWS.url("/").toString()

        val retrofit = Retrofit.Builder()
            .baseUrl(expectedBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        RetrofitClient.retrofitInstance = retrofit
        val retrofitInstace = RetrofitClient.retrofitInstance
        Assert.assertEquals(expectedBaseUrl, retrofitInstace.baseUrl().toString())

    }
}