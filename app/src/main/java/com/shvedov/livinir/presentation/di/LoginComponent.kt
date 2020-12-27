package com.shvedov.livinir.presentation.di

import com.shvedov.livinir.presentation.login.LoginFragment
import dagger.Component
import dagger.Subcomponent

@Subcomponent(
    modules = [
        LoginModule::class
    ]
)
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}