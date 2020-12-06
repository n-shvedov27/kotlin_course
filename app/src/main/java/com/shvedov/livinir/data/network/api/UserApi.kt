package com.shvedov.livinir.data.network.api

import com.shvedov.livinir.data.network.api.request.LoginRequest
import com.shvedov.livinir.data.network.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @POST("/api/v1/registration")
    fun registration(@Body user: User): Call<User>

    @POST("/api/v1/login")
    fun login(@Body user: LoginRequest): Call<User>
}

