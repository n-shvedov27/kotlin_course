package com.shvedov.livinir.di.registration

import com.shvedov.livinir.presentation.screen.registration.RegistrationFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        RegistrationModule::class
    ]
)
interface RegistrationComponent {

    fun inject(registrationFragment: RegistrationFragment)
}