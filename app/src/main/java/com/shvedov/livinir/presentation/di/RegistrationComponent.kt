package com.shvedov.livinir.presentation.di

import com.shvedov.livinir.presentation.registration.RegistrationFragment
import dagger.Subcomponent

@Subcomponent
interface RegistrationComponent {

    fun inject(registrationFragment: RegistrationFragment)
}