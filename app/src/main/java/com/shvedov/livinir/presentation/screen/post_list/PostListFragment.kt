package com.shvedov.livinir.presentation.screen.post_list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.shvedov.livinir.R
import com.shvedov.livinir.data.mapper.PostDbToPostMapper
import com.shvedov.livinir.di.app.DaggerAppComponent
import com.shvedov.livinir.presentation.core_ui.BaseFragment
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListFragment : BaseFragment<PostListViewModel>() {

    override val viewModel: PostListViewModel by injectViewModel()
    override val layoutResId = R.layout.fragment_post_list

    private lateinit var postListRecyclerView: RecyclerView
    private val adapter = PostAdapter(emptyList()) {

        val action = PostListFragmentDirections.actionPostListFragmentToPostInfoFragment(it.author.username, it.text, it.title)
        findNavController().navigate(action)
    }

    @Inject
    lateinit var mapperDb: PostDbToPostMapper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.create().postListComponent().inject(this)
    }

    private fun showError(errorMessage: String) {

        Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun pullPosts() = Completable.create {

        kotlin.runCatching {

            viewModel.pullAllPosts()
            it.onComplete()

        }.onFailure { e -> it.onError(e) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.post_list_fragment_create_post).setOnClickListener {

            findNavController().navigate(R.id.action_postListFragment_to_createPostFragment)
        }

        viewModel.getAllPost().addChangeListener { result ->
            adapter.updatePosts(result.map { mapperDb.invoke(it) })
        }

        postListRecyclerView = view.findViewById(R.id.post_list_fragment_rv)
        postListRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        pullPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorComplete {
                showError(it.message ?: it.toString())
                true
            }.subscribe()
    }
}