package com.shvedov.livinir.presentation.login

import androidx.lifecycle.ViewModel
import com.shvedov.livinir.data.repository.UserRepository
import com.shvedov.livinir.presentation.entity.User
import javax.inject.Inject

class LoginViewModel @Inject constructor(

    private val userRepository: UserRepository

) : ViewModel() {

    fun login(email: String, password: String) : User = userRepository.login(email, password)
}