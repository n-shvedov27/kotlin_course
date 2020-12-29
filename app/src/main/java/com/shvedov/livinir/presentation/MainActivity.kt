package com.shvedov.livinir.presentation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.shvedov.livinir.R
import com.shvedov.livinir.data.repository.PostRepository
import com.shvedov.livinir.di.app.DaggerAppComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity(), AuthService {

    var appState: AppState = NonAuthorized

    private lateinit var navController: NavController

    companion object {
        const val USER_KEY = "shared_pref_user_key"
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

        appState = Authorized(userId)

        getPreferences(Context.MODE_PRIVATE).edit()
            .putString(USER_KEY, userId)
            .apply()
    }

    override fun onLogout() {

        getPreferences(Context.MODE_PRIVATE).edit()
            .remove(USER_KEY)
            .apply()

        navController.navigate(R.id.action_global_loginFragment)
    }
}