package com.shvedov.livinir.presentation.screen.create_post

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.shvedov.livinir.R
import com.shvedov.livinir.di.app.DaggerAppComponent
import com.shvedov.livinir.presentation.MainActivity.Companion.USER_KEY
import com.shvedov.livinir.presentation.core_ui.BaseFragment
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreatePostFragment : BaseFragment<CreatePostViewModel>() {

    override val layoutResId = R.layout.fragment_create_post

    private lateinit var titleEditText: EditText
    private lateinit var textEditText: EditText

    override val viewModel: CreatePostViewModel by injectViewModel()

    private lateinit var userId: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.create().createPostComponent().inject(this)
    }

    private fun createPost(title: String, text: String, authorId: String) = Completable.create {

        kotlin.runCatching {

            viewModel.createPost(title, text, authorId)
            it.onComplete()

        }.onFailure { e -> it.onError(e) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = requireActivity().getPreferences(Context.MODE_PRIVATE).getString(USER_KEY, "")!!

        titleEditText = view.findViewById(R.id.create_post_fragment_title_hint)
        textEditText = view.findViewById(R.id.create_post_fragment_text_hint)

        view.findViewById<Button>(R.id.create_post_fragment_create_btn).setOnClickListener {

            if (isValidInfo()) {

                createPost(
                    title = titleEditText.text.toString(),
                    text = textEditText.text.toString(),
                    authorId = userId
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ findNavController().popBackStack() }, { showError(it.message ?: it.toString()) })
            }
            else {
                showError("Неверные данные")
            }
        }
    }

    private fun isValidInfo() = titleEditText.text.isNotEmpty() && textEditText.text.isNotEmpty()

    private fun showError(errorMessage: String) {

        Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}