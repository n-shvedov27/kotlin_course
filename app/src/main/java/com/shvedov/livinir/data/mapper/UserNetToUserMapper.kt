package com.shvedov.livinir.data.mapper

import com.shvedov.livinir.data.network.entity.UserNet
import com.shvedov.livinir.presentation.entity.User
import javax.inject.Inject

class UserNetToUserMapper @Inject constructor(

) : (UserNet) -> User {

    override fun invoke(model: UserNet): User {
        return User(
            id = model.id!!,
            username = model.username,
            email = model.email,
            password = model.password
        )
    }
}