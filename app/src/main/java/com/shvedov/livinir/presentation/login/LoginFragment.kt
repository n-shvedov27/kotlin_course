package com.shvedov.livinir.presentation.login

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
import com.shvedov.livinir.data.network.entity.User
import com.shvedov.livinir.data.network.repository.UserRepository
import com.shvedov.livinir.presentation.AuthNavigation
import java.lang.Exception
import java.lang.ref.WeakReference

class LoginFragment : Fragment() {

    companion object {
        private const val LOGIN_FAILED = 0
        private const val LOGIN_SUCCESS = 1
    }

    private val userRepository = UserRepository()
    private lateinit var passwordEditText: EditText
    private lateinit var emailEditText: EditText

    var handler: WeakReference<AuthNavigation>? = null

    fun attachToHandler(handler: AuthNavigation) {
        this.handler = WeakReference(handler)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText = view.findViewById(R.id.login_fragment_email)
        passwordEditText = view.findViewById(R.id.login_fragment_password)

        view.findViewById<Button>(R.id.login_fragment_login).setOnClickListener {

            if (isValidInfo()) {

                login()
            } else {

                Toast.makeText(requireContext(), "Неверные данные", Toast.LENGTH_SHORT).show()

            }
        }

        view.findViewById<Button>(R.id.login_fragment_registration).setOnClickListener {

            handler?.get()?.openRegistrationScreen()
        }
    }

    private fun login(){

        val handler = Handler(Looper.getMainLooper()) {

            when (it.what) {
                LOGIN_FAILED -> {
                    Toast.makeText(requireContext(), it.obj as String, Toast.LENGTH_SHORT).show()
                }
                LOGIN_SUCCESS -> {
                    handler?.get()?.onAuthSuccess((it.obj as User).id!!)
                }
            }
            true
        }


        Thread {

            val msg = try {

                val user = userRepository.login(
                    email = emailEditText.text.toString(),
                    password = passwordEditText.text.toString()
                )
                handler.obtainMessage(LOGIN_SUCCESS, user)
            } catch (e: Exception) {
                handler.obtainMessage(LOGIN_FAILED, e.message)
            }
            handler.sendMessage(msg)

        }.start()
    }

    private fun isValidInfo() = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
}