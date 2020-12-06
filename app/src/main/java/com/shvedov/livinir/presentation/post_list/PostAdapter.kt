package com.shvedov.livinir.presentation.post_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shvedov.livinir.R
import com.shvedov.livinir.data.network.entity.Post

class PostAdapter(

    private var posts: List<Post>,
    private val onClick: (post: Post) -> Unit

): RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.li_post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onClick.invoke(posts[position])
        }
        holder.bindPost(posts[position])
    }

    fun updatePosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }
}