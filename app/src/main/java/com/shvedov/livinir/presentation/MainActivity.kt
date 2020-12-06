package com.shvedov.livinir.presentation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.shvedov.livinir.R

class MainActivity : AppCompatActivity(), AuthService {

    private lateinit var navController: NavController

    companion object {
        const val USER_KEY = "shared_pref_user_key"
        private const val LIST_SCREEN = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onAuthSuccess(userId: String) {

        getPreferences(Context.MODE_PRIVATE).edit()
            .putString(USER_KEY, userId)
            .apply()

        (application as App).state = AppState.AUTHORIZED
    }
}