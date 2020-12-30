package com.shvedov.livinir.di.user_info

import androidx.lifecycle.ViewModel
import com.shvedov.livinir.di.keys.ViewModelKey
import com.shvedov.livinir.presentation.screen.user_info.UserInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserInfoModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    abstract fun bindViewModel(viewModel: UserInfoViewModel): ViewModel
}