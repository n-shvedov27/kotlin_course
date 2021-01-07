package com.shvedov.livinir.presentation.screen.post_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shvedov.livinir.R
import com.shvedov.livinir.presentation.entity.Post

class PostViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

//    private val image: ImageView = mView.findViewById(R.id.item_image)
//    private val name: TextView = mView.findViewById(R.id.item_name)

    companion object {
        private const val MAX_TEXT_LENGTH = 300
    }

    fun bindPost(post: Post) {

        view.transitionName = post.id

        view.findViewById<TextView>(R.id.li_post_title).text = post.title
        view.findViewById<TextView>(R.id.li_post_author).text = post.author.username
        view.findViewById<TextView>(R.id.li_post_text).text = post.text.take(MAX_TEXT_LENGTH, "...")
    }


    private fun String.take(n: Int, suffix: String) = when (length > n) {
        true -> take(n) + suffix
        false -> this
    }
}