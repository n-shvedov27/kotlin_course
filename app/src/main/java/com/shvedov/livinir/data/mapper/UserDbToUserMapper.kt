package com.shvedov.livinir.data.mapper

import com.shvedov.livinir.data.db.entity.UserDb
import com.shvedov.livinir.presentation.entity.User
import javax.inject.Inject

class UserDbToUserMapper @Inject constructor(

): (UserDb) -> User {

    override fun invoke(model: UserDb): User {
        return User(
            id = model.id,
            username = model.username!!,
            email = model.email!!,
            password = model.password!!
        )
    }
}