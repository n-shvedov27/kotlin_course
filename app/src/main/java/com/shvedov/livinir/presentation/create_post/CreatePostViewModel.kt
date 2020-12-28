package com.shvedov.livinir.presentation.create_post

import androidx.lifecycle.ViewModel
import com.shvedov.livinir.data.repository.PostRepository
import javax.inject.Inject

class CreatePostViewModel @Inject constructor(

    private val postRepository: PostRepository

) : ViewModel() {

    fun createPost(title: String, text: String, authorId: String) {
        return postRepository.createPost(title, text, authorId)
    }
}