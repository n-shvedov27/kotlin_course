package com.shvedov.livinir.presentation.post_list

import androidx.lifecycle.ViewModel
import com.shvedov.livinir.data.db.entity.PostDb
import com.shvedov.livinir.data.repository.PostRepository
import io.realm.RealmResults
import javax.inject.Inject

class PostListViewModel @Inject constructor(

    private val postRepository: PostRepository

) : ViewModel() {

    fun pullAllPosts() {
        postRepository.pullAllPosts()
    }

    fun getAllPost(): RealmResults<PostDb> {
        return postRepository.getAllPost()
    }
}