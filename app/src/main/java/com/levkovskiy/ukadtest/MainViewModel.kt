package com.levkovskiy.ukadtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.levkovskiy.ukadtest.network.Api
import com.levkovskiy.ukadtest.network.RetrofitService

class MainViewModel : ViewModel() {

    val tokenLiveData = MutableLiveData<String>()
    val apiService = RetrofitService().createService(Api::class.java)

    fun login() = apiService.login("Augustus", "5E065CDCC904FA398B8B0DB3AA268E9F", 1)

    fun getDataPerQuartet(headers: Map<String, String>) = apiService.getDataPerQuartet(headers)
    fun getDataPerMonth(headers: Map<String, String>) = apiService.getDataPerMonth(headers)
    fun getDataPerWeek(headers: Map<String, String>) = apiService.getDataPerWeek(headers)
}