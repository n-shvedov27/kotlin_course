package com.shvedov.livinir.presentation.di

import com.shvedov.livinir.presentation.post_list.PostListFragment
import dagger.Subcomponent

@Subcomponent
interface PostListComponent {

    fun inject(fragment: PostListFragment)
}