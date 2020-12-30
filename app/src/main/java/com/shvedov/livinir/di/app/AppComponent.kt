package com.shvedov.livinir.di.app

import com.shvedov.livinir.di.create_post.CreatePostComponent
import com.shvedov.livinir.di.login.LoginComponent
import com.shvedov.livinir.di.post_list.PostListComponent
import com.shvedov.livinir.di.registration.RegistrationComponent
import com.shvedov.livinir.di.user_info.UserInfoComponent
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
    fun userInfoComponent(): UserInfoComponent
    fun createPostComponent(): CreatePostComponent
    fun postListComponent(): PostListComponent
}