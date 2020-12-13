package com.shvedov.livinir.data.network.api

import com.shvedov.livinir.data.network.api.request.LoginRequest
import com.shvedov.livinir.data.network.entity.UserNet
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/api/v1/registration")
    fun registration(@Body user: UserNet): Call<UserNet>

    @POST("/api/v1/login")
    fun login(@Body user: LoginRequest): Call<UserNet>
}

