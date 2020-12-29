package com.shvedov.livinir.di.post_list

import com.shvedov.livinir.presentation.screen.post_list.PostListFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        PostListModule::class
    ]
)
interface PostListComponent {

    fun inject(fragment: PostListFragment)
}