package com.shvedov.livinir

interface AuthNavigation {

    fun openLoginScreen()

    fun openRegistrationScreen()

    fun onAuthSuccess(userId: String)
}