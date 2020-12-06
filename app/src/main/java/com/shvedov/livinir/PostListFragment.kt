package com.shvedov.livinir

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.shvedov.livinir.data.network.entity.Post
import com.shvedov.livinir.data.network.repository.PostRepository
import java.lang.ref.WeakReference

class PostListFragment: Fragment() {

    companion object {
        const val GET_POSTS_SUCCESS = 0
        const val GET_POSTS_FAIL = 1
    }

    private lateinit var postListRecyclerView: RecyclerView
    private val adapter = PostAdapter(emptyList()) {
        handler?.get()?.openPostInfo(it)
    }
    private val postRepository = PostRepository()
    var handler: WeakReference<AppNavigation>? = null

    fun attachToHandler(handler: AppNavigation) {
        this.handler = WeakReference(handler)
    }

    private fun showError(errorMessage: String) {

        Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.post_list_fragment_create_post).setOnClickListener {
            handler?.get()?.openCreatePostScreen()
        }

        postListRecyclerView = view.findViewById(R.id.post_list_fragment_rv)
        postListRecyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()

        val handler = Handler(Looper.getMainLooper()) {

            when (it.what) {
                GET_POSTS_FAIL -> showError(it.obj as String)
                GET_POSTS_SUCCESS -> {

                    val posts = it.obj as List<Post>
                    adapter.updatePosts(posts)
                }
            }

            true
        }

        GetPostsThread(
            handler = handler,
            repository = postRepository
        ).start()
    }
}

class GetPostsThread(

    private val handler: Handler,
    private val repository: PostRepository

) : Thread() {

    override fun run() {

        val msg = try {

            val posts = repository.getAllPost()
            handler.obtainMessage(PostListFragment.GET_POSTS_SUCCESS, posts)
        } catch (e: Exception) {

            handler.obtainMessage(PostListFragment.GET_POSTS_FAIL, e.message)
        }

        handler.sendMessage(msg)
    }
}