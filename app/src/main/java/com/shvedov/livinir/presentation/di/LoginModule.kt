package com.shvedov.livinir.presentation.di

import androidx.lifecycle.ViewModel
import com.shvedov.livinir.di.keys.ViewModelKey
import com.shvedov.livinir.presentation.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindViewModel(viewModel: LoginViewModel): ViewModel
}