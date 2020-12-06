package com.shvedov.livinir

import com.shvedov.livinir.data.network.entity.Post

interface AppNavigation {

    fun openPostListScreen()

    fun openCreatePostScreen()

    fun openPostInfo(post: Post)
}