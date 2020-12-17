package com.shvedov.livinir.data.repository

import com.google.gson.GsonBuilder
import com.shvedov.livinir.data.mapper.UserNetToUserMapper
import com.shvedov.livinir.data.network.api.UserApi
import com.shvedov.livinir.data.network.api.request.LoginRequest
import com.shvedov.livinir.data.network.entity.UserNet
import com.shvedov.livinir.presentation.entity.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(

    private val api: UserApi,
    private val mapperNet: UserNetToUserMapper

) : UserRepository {

    companion object {
        private val api = Retrofit.Builder()
            .baseUrl("http://192.168.1.185/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(UserApi::class.java)
    }


    override fun registration(username: String, email: String, password: String): User {

        val user = UserNet(
            username = username,
            email = email,
            password = password
        )

        val response = api.registration(user).execute()
        if (response.isSuccessful) {

            return mapperNet.invoke(
                model = response.body() ?: error("Body is null")
            )
        } else {
            throw RuntimeException(response.errorBody()?.string())
        }
    }

    override fun login(email: String, password: String): User {
        val request = LoginRequest(email, password)
        val response = api.login(request).execute()
        if (response.isSuccessful) {
            return mapperNet.invoke(
                model = response.body() ?: error("Body is null")
            )
        } else {
            throw RuntimeException(response.errorBody()?.string())
        }
    }
}