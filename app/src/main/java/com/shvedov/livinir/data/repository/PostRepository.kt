package com.shvedov.livinir.data.repository

import com.shvedov.livinir.data.db.entity.PostDb
import com.shvedov.livinir.data.db.entity.UserDb
import com.shvedov.livinir.data.network.api.request.CreatePostRequest
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

interface PostRepository {

    fun getAllPost(): RealmResults<PostDb>

    fun pullAllPosts()

    fun createPost(title: String, text: String, authorId: String)
}