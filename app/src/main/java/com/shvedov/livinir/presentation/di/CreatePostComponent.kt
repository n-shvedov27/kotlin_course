package com.shvedov.livinir.presentation.di

import com.shvedov.livinir.presentation.create_post.CreatePostFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        CreatePostModule::class
    ]
)
interface CreatePostComponent {

    fun inject(createPostFragment: CreatePostFragment)
}