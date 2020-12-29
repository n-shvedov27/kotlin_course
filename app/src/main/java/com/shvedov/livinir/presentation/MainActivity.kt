package com.shvedov.livinir.presentation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.shvedov.livinir.R
import com.shvedov.livinir.data.repository.PostRepository
import com.shvedov.livinir.di.app.DaggerAppComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity(), AuthService {

    private lateinit var navController: NavController

    companion object {
        const val USER_KEY = "shared_pref_user_key"
        private const val LIST_SCREEN = 2
    }

    @Inject
    lateinit var posRepository: PostRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerAppComponent.create().inject(this)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onAuthSuccess(userId: String) {

        getPreferences(Context.MODE_PRIVATE).edit()
            .putString(USER_KEY, userId)
            .apply()
    }
}