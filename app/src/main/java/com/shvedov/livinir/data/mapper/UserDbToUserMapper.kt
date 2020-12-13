package com.shvedov.livinir.data.mapper

import com.shvedov.livinir.data.db.entity.UserDb
import com.shvedov.livinir.presentation.entity.User

class UserDbToUserMapper: (UserDb) -> User {

    override fun invoke(model: UserDb): User {
        return User(
            id = model.id,
            username = model.username!!,
            email = model.email!!,
            password = model.password!!
        )
    }
}