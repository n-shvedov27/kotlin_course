package com.shvedov.livinir.presentation.di

import com.shvedov.livinir.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        ViewModelBindModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)

    fun plusLoginComponent(): LoginComponent
    fun registrationComponent(): RegistrationComponent
    fun createPostComponent(): CreatePostComponent
    fun postListComponent(): PostListComponent
}