package com.shvedov.livinir.di.create_post

import com.shvedov.livinir.presentation.screen.create_post.CreatePostFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        CreatePostModule::class
    ]
)
interface CreatePostComponent {

    fun inject(createPostFragment: CreatePostFragment)
}