package com.shvedov.livinir.presentation.di

import androidx.lifecycle.ViewModel
import com.shvedov.livinir.di.keys.ViewModelKey
import com.shvedov.livinir.presentation.create_post.CreatePostViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CreatePostModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreatePostViewModel::class)
    abstract fun bindViewModel(viewModel: CreatePostViewModel): ViewModel
}