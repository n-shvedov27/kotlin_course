package com.shvedov.livinir.data.repository

import com.shvedov.livinir.presentation.entity.User

interface UserRepository {

    fun registration(username: String, email: String, password: String): User

    fun login(email: String, password: String): User
}