package com.shvedov.livinir.data.network.api

import com.shvedov.livinir.data.network.api.request.CreatePostRequest
import com.shvedov.livinir.data.network.entity.PostNet
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostApi {

    @GET("/api/v1/post")
    fun getAllPosts(): Call<List<PostNet>>

    @POST("/api/v1/post")
    fun createPost(@Body request: CreatePostRequest): Call<PostNet>
}