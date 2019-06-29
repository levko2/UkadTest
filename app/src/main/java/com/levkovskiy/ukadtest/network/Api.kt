package com.levkovskiy.ukadtest.network

import androidx.lifecycle.LiveData
import com.levkovskiy.ukadtest.network.factory.ApiResponse
import retrofit2.Call
import retrofit2.http.*


interface Api {
    @FormUrlEncoded
    @POST("Account/Login")
    fun login(
        @Field("UserName") userName: String,
        @Field("Password") password: String,
        @Field("TenantID") tenantId: Int
    ): LiveData<ApiResponse<LoginResponse>>


    @GET("Trackers/Steps/WithGoal/Quarter")
    fun getDataPerQuartet(@HeaderMap headers: Map<String, String>): LiveData<ApiResponse<ChartResponse>>

    @GET("Trackers/Steps/WithGoal/Month")
    fun getDataPerMonth(@HeaderMap headers: Map<String, String>): LiveData<ApiResponse<ChartResponse>>

    @GET("Trackers/Steps/WithGoal/Week")
    fun getDataPerWeek(@HeaderMap headers: Map<String, String>): LiveData<ApiResponse<ChartResponse>>
}