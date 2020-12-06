package com.shvedov.livinir.data.network.repository

import com.google.gson.GsonBuilder
import com.shvedov.livinir.data.network.api.UserApi
import com.shvedov.livinir.data.network.api.request.LoginRequest
import com.shvedov.livinir.data.network.entity.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException

class UserRepository {

    private val api = Retrofit.Builder()
        .baseUrl("http://192.168.43.185/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(UserApi::class.java)

    fun registration(username: String, email: String, password: String): User? {

        val user = User(
            username = username,
            email = email,
            password = password
        )

        val response = api.registration(user).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw RuntimeException(response.errorBody()?.string())
        }
    }

    fun login(email: String, password: String): User? {
        val request = LoginRequest(email, password)
        val response = api.login(request).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw RuntimeException(response.errorBody()?.string())
        }
    }
}