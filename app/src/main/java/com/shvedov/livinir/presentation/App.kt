package com.shvedov.livinir.presentation

import android.app.Application

class App: Application() {

    var state = AppState.UNAUTHORIZED

    val isAuthorized
    get() = state == AppState.AUTHORIZED
}