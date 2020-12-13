package com.shvedov.livinir.data.network.entity

import com.google.gson.annotations.SerializedName

data class UserNet(

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)