package com.shvedov.livinir.presentation.di

import androidx.lifecycle.ViewModel
import com.shvedov.livinir.di.keys.ViewModelKey
import com.shvedov.livinir.presentation.login.LoginViewModel
import com.shvedov.livinir.presentation.post_list.PostListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PostListModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostListViewModel::class)
    abstract fun bindViewModel(viewModel: PostListViewModel): ViewModel

}