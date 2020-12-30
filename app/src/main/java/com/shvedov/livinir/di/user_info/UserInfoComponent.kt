package com.shvedov.livinir.di.user_info

import com.shvedov.livinir.di.registration.RegistrationModule
import com.shvedov.livinir.presentation.screen.registration.RegistrationFragment
import com.shvedov.livinir.presentation.screen.user_info.UserInfoFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        UserInfoModule::class
    ]
)
interface UserInfoComponent {

    fun inject(userInfoFragment: UserInfoFragment)

}