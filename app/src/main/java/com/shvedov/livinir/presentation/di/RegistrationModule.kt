package com.shvedov.livinir.presentation.di

import androidx.lifecycle.ViewModel
import com.shvedov.livinir.di.keys.ViewModelKey
import com.shvedov.livinir.presentation.registration.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RegistrationModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindViewModel(viewModel: RegistrationViewModel): ViewModel

}