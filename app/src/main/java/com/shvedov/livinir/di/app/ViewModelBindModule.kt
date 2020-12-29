package com.shvedov.livinir.di.app

import androidx.lifecycle.ViewModelProvider
import com.shvedov.livinir.di.keys.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBindModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}