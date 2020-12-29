package com.shvedov.livinir.presentation.screen.registration

import androidx.lifecycle.ViewModel
import com.shvedov.livinir.data.repository.UserRepository
import com.shvedov.livinir.presentation.entity.User
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(

    private val userRepository: UserRepository

) : ViewModel() {

    fun registration(username: String, email: String, password: String) : User {
        return userRepository.registration(username, email, password)
    }
}