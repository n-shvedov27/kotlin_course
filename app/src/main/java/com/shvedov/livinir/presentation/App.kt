package com.shvedov.livinir.presentation

import android.app.Application
import io.realm.Realm

class App: Application() {

    var state = AppState.UNAUTHORIZED

    val isAuthorized
    get() = state == AppState.AUTHORIZED

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}