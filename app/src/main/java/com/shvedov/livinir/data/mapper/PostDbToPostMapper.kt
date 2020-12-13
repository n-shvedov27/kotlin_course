package com.shvedov.livinir.data.mapper

import com.shvedov.livinir.data.db.entity.PostDb
import com.shvedov.livinir.presentation.entity.Post

class PostDbToPostMapper : (PostDb) -> Post {

    private val userDbToUserMapper = UserDbToUserMapper()

    override fun invoke(model: PostDb): Post {
        return Post(
            id = model.id,
            text = model.text!!,
            title = model.title!!,
            author = userDbToUserMapper.invoke(model.author!!)
        )
    }
}