package com.shvedov.livinir.data.network.repository

import com.google.gson.GsonBuilder
import com.shvedov.livinir.data.network.api.PostApi
import com.shvedov.livinir.data.network.api.UserApi
import com.shvedov.livinir.data.network.api.request.CreatePostRequest
import com.shvedov.livinir.data.network.entity.Post
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException

class PostRepository {

    private val api = Retrofit.Builder()
        .baseUrl("http://192.168.43.185/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(PostApi::class.java)

    fun getAllPost(): List<Post>? {

        val response = api.getAllPosts().execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw RuntimeException(response.errorBody()?.string())
        }
    }

    fun createPost(title: String, text: String, authorId: String): Post? {

        val request = CreatePostRequest(
            text, title, authorId
        )
        val response = api.createPost(request).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw RuntimeException(response.errorBody()?.string())
        }
    }
}