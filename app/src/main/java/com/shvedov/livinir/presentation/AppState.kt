package com.shvedov.livinir.presentation

sealed class AppState

data class Authorized(val userId: String) : AppState()
object NonAuthorized: AppState()


