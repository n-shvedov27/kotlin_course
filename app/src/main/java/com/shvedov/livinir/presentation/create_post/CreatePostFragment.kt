package com.shvedov.livinir.presentation.create_post

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.shvedov.livinir.R
import com.shvedov.livinir.presentation.create_post.CreatePostFragment.Companion.CREATE_POST_FAILED
import com.shvedov.livinir.presentation.create_post.CreatePostFragment.Companion.CREATE_POST_SUCCESS
import com.shvedov.livinir.presentation.MainActivity.Companion.USER_KEY
import com.shvedov.livinir.data.network.repository.PostRepository
import com.shvedov.livinir.presentation.AppNavigation
import com.shvedov.livinir.presentation.registration.RegistrationFragment
import java.lang.Exception
import java.lang.ref.WeakReference

class CreatePostFragment : Fragment() {

    companion object {
        const val CREATE_POST_FAILED = 1
        const val CREATE_POST_SUCCESS = 2
    }
    private val postRepository = PostRepository()
    private lateinit var titleEditText: EditText
    private lateinit var textEditText: EditText

    private lateinit var userId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = requireActivity().getPreferences(Context.MODE_PRIVATE).getString(USER_KEY, "")!!

        titleEditText = view.findViewById(R.id.create_post_fragment_title)
        textEditText = view.findViewById(R.id.create_post_fragment_text)

        view.findViewById<Button>(R.id.create_post_fragment_create_btn).setOnClickListener {

            if (isValidInfo()) {

                val handler = Handler(Looper.getMainLooper()) {

                    when (it.what) {
                        CREATE_POST_FAILED -> showError(it.obj as String)
                        RegistrationFragment.REGISTRATION_SUCCESS -> handler?.get()?.openPostListScreen()
                    }

                    true
                }

                CreatePostThread(
                    handler = handler,
                    repository = postRepository,
                    title = titleEditText.text.toString(),
                    text = textEditText.text.toString(),
                    authorId = userId
                ).start()

            } else {
                showError("Неверные данные")
            }
        }
    }

    private fun isValidInfo() = titleEditText.text.isNotEmpty() && textEditText.text.isNotEmpty()


    var handler: WeakReference<AppNavigation>? = null

    fun attachToHandler(handler: AppNavigation) {
        this.handler = WeakReference(handler)
    }

    private fun showError(errorMessage: String) {

        Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}

class CreatePostThread(

    private val handler: Handler,
    private val repository: PostRepository,
    private val title: String,
    private val text: String,
    private val authorId: String

) : Thread() {

    override fun run() {

        val msg = try {

            val user = repository.createPost(title, text, authorId)
            handler.obtainMessage(CREATE_POST_SUCCESS, user)
        } catch (e: Exception) {

            handler.obtainMessage(CREATE_POST_FAILED, e.message)
        }

        handler.sendMessage(msg)
    }
}