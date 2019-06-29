package com.levkovskiy.ukadtest.network

import com.levkovskiy.ukadtest.network.factory.LiveDataCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class RetrofitService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dev-mdt-api.wellnesslayers.com/API/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()


    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}