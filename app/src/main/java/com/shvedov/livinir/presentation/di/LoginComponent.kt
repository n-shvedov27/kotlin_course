package com.shvedov.livinir.presentation.di

import com.shvedov.livinir.presentation.login.LoginFragment
import dagger.Component
import dagger.Subcomponent

@Subcomponent
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}