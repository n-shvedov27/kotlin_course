package com.shvedov.livinir.data.repository

import com.google.gson.GsonBuilder
import com.shvedov.livinir.data.db.entity.PostDb
import com.shvedov.livinir.data.db.entity.UserDb
import com.shvedov.livinir.data.mapper.PostDbToPostMapper
import com.shvedov.livinir.data.mapper.PostNetToPostDbMapper
import com.shvedov.livinir.data.mapper.PostNetToPostMapper
import com.shvedov.livinir.data.network.api.PostApi
import com.shvedov.livinir.data.network.api.request.CreatePostRequest
import com.shvedov.livinir.presentation.entity.Post
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(

    private val api: PostApi

) : PostRepository {

    private val mapperNetToDb = PostNetToPostDbMapper()

    override fun getAllPost(): RealmResults<PostDb> {

        val realm = Realm.getDefaultInstance()
        return realm.where<PostDb>().findAllAsync()
    }

    override fun pullAllPosts() {

        val response = api.getAllPosts().execute()

        if (response.isSuccessful) {

            val body = response.body() ?: error("Empty response body")

            val realm = Realm.getDefaultInstance()

            realm.executeTransaction {
                it.copyToRealmOrUpdate(body.map(mapperNetToDb))
            }
        }
        else {
            throw RuntimeException(response.errorBody()?.string())
        }
    }

    override fun createPost(title: String, text: String, authorId: String) {

        val request = CreatePostRequest(text, title, authorId)

        val response = api.createPost(request).execute()
        if (response.isSuccessful) {

            val body = response.body() ?: error("Empty response body")

            val realm = Realm.getDefaultInstance()

            realm.executeTransaction {

                val post = PostDb(
                    id = body.id,
                    text = body.text,
                    title = body.title,
                    author = UserDb(
                        id = body.author.id!!,
                        username = body.author.username,
                        email = body.author.email,
                        password = body.author.password
                    )
                )

                it.copyToRealmOrUpdate(post)
            }
        }
        else {
            throw RuntimeException(response.errorBody()?.string())
        }
    }
}