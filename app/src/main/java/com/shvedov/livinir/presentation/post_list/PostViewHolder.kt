package com.shvedov.livinir.presentation.post_list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shvedov.livinir.R
import com.shvedov.livinir.data.db.entity.PostDb
import com.shvedov.livinir.data.network.entity.PostNet
import com.shvedov.livinir.presentation.entity.Post

class PostViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        private const val MAX_TEXT_LENGTH = 300
    }

    fun bindPost(post: Post) {
        view.findViewById<TextView>(R.id.li_post_title).text = post.title
        view.findViewById<TextView>(R.id.li_post_author).text = post.author.username
        view.findViewById<TextView>(R.id.li_post_text).text = post.text.take(MAX_TEXT_LENGTH, "...")
    }


    private fun String.take(n: Int, suffix: String) = when (length > n) {
        true -> take(n) + suffix
        false -> this
    }
}