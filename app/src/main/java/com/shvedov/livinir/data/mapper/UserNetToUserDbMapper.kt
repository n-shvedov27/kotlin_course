package com.shvedov.livinir.data.mapper

import com.shvedov.livinir.data.db.entity.UserDb
import com.shvedov.livinir.data.network.entity.UserNet
import com.shvedov.livinir.presentation.entity.User
import dagger.Reusable
import javax.inject.Inject

@Reusable
class UserNetToUserDbMapper @Inject constructor(

) : (UserNet) -> UserDb {

    override fun invoke(model: UserNet): UserDb {
        return UserDb(
            id = model.id!!,
            username = model.username,
            email = model.email,
            password = model.password
        )
    }
}