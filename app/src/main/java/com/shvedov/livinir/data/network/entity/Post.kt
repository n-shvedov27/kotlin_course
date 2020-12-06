package com.shvedov.livinir.data.network.entity

import com.google.gson.annotations.SerializedName

data class Post(

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("title")
    val title: String,

    @SerializedName("text")
    val text: String,

    @SerializedName("author")
    val author: User
)