package com.shvedov.livinir.presentation

interface AuthService {

    fun onAuthSuccess(userId: String)

    fun onLogout()
}