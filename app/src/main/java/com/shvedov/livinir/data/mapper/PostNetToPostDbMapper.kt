package com.shvedov.livinir.data.mapper

import com.shvedov.livinir.data.db.entity.PostDb
import com.shvedov.livinir.data.network.entity.PostNet
import com.shvedov.livinir.presentation.entity.Post

class PostNetToPostDbMapper : (PostNet) -> PostDb {

    private val userNetToUserMapper = UserNetToUserDbMapper()

    override fun invoke(model: PostNet): PostDb {
        return PostDb(
            id = model.id,
            text = model.text,
            title = model.title,
            author = userNetToUserMapper.invoke(model.author)
        )
    }
}