package com.shvedov.livinir.di.login

import com.shvedov.livinir.presentation.screen.login.LoginFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        LoginModule::class
    ]
)
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}