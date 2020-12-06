package com.shvedov.livinir.data.network.api.request

import com.google.gson.annotations.SerializedName

class CreatePostRequest(

    @SerializedName("text")
    val text: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("author_id")
    val authorId: String
)