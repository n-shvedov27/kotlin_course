package com.shvedov.livinir.presentation

interface AuthNavigation {

    fun openLoginScreen()

    fun openRegistrationScreen()

    fun onAuthSuccess(userId: String)
}