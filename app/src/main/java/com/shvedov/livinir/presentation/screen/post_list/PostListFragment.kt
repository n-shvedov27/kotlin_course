package com.shvedov.livinir.presentation.screen.post_list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.shvedov.livinir.R
import com.shvedov.livinir.data.mapper.PostDbToPostMapper
import com.shvedov.livinir.di.app.DaggerAppComponent
import com.shvedov.livinir.presentation.Authorized
import com.shvedov.livinir.presentation.MainActivity
import com.shvedov.livinir.presentation.core_ui.BaseFragment
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListFragment : BaseFragment<PostListViewModel>() {

    override val viewModel: PostListViewModel by injectViewModel()
    override val layoutResId = R.layout.fragment_post_list

    private lateinit var postListRecyclerView: RecyclerView
    private val adapter = PostAdapter(emptyList()) { post, view ->

        val extras = FragmentNavigatorExtras(
            view to post.id
        )

        this.sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.li_transition)
        this.exitTransition = null

        val action = PostListFragmentDirections.actionPostListFragmentToPostInfoFragment(post.id, post.author.username, post.text, post.title)
        findNavController().navigate(action, extras)
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

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        view.findViewById<Button>(R.id.post_list_fragment_create_post).setOnClickListener {

            findNavController().navigate(R.id.action_postListFragment_to_createPostFragment)
        }

        view.findViewById<View>(R.id.post_list_fragment_current_user_info).setOnClickListener {
            val userId = ((requireActivity() as MainActivity).appState as Authorized).userId
            val action = PostListFragmentDirections.actionPostListFragmentToUserInfoFragment(userId)
            findNavController().navigate(action)
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