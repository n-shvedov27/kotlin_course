package com.shvedov.livinir.data.mapper

import com.shvedov.livinir.data.network.entity.PostNet
import com.shvedov.livinir.data.network.entity.UserNet
import com.shvedov.livinir.presentation.entity.Post
import com.shvedov.livinir.presentation.entity.User

class PostNetToPostMapper : (PostNet) -> Post {

    private val userNetToUserMapper = UserNetToUserMapper()

    override fun invoke(model: PostNet): Post {
        return Post(
            id = model.id,
            text = model.text,
            title = model.title,
            author = userNetToUserMapper.invoke(model.author)
        )
    }
}