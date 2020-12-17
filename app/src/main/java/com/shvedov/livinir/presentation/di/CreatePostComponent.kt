package com.shvedov.livinir.presentation.di

import com.shvedov.livinir.presentation.create_post.CreatePostFragment
import dagger.Subcomponent

@Subcomponent
interface CreatePostComponent {

    fun inject(createPostFragment: CreatePostFragment)
}