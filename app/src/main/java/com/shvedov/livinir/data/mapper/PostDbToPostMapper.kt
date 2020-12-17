package com.shvedov.livinir.data.mapper

import com.shvedov.livinir.data.db.entity.PostDb
import com.shvedov.livinir.presentation.entity.Post
import javax.inject.Inject

class PostDbToPostMapper @Inject constructor(

    private val userDbToUserMapper: UserDbToUserMapper

) : (PostDb) -> Post {

    override fun invoke(model: PostDb): Post {
        return Post(
            id = model.id,
            text = model.text!!,
            title = model.title!!,
            author = userDbToUserMapper.invoke(model.author!!)
        )
    }
}