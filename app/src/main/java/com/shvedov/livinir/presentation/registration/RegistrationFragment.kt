package com.shvedov.livinir.presentation.registration

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shvedov.livinir.R
import com.shvedov.livinir.data.network.entity.User
import com.shvedov.livinir.data.network.repository.UserRepository
import com.shvedov.livinir.presentation.AuthService
import com.shvedov.livinir.utils.extension.requireActivityAs
import java.lang.Exception

class RegistrationFragment : Fragment() {

    companion object {
        const val REGISTRATION_FAILED = 1
        const val REGISTRATION_SUCCESS = 2
    }

    private val userRepository = UserRepository()

    private lateinit var passwordEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameEditText = view.findViewById(R.id.name)
        emailEditText = view.findViewById(R.id.email)
        passwordEditText = view.findViewById(R.id.password)

        view.findViewById<Button>(R.id.registration).setOnClickListener {

            if (isValidInfo()) {

                val handler = Handler(Looper.getMainLooper()) {

                    when (it.what) {
                        REGISTRATION_FAILED -> showError(it.obj as String)
                        REGISTRATION_SUCCESS -> {
                            requireActivityAs<AuthService>().onAuthSuccess((it.obj as User).id!!)

                            findNavController().navigate(R.id.action_registrationFragment_to_postListFragment)
                        }
                    }

                    true
                }

                RegistrationThread(
                    handler = handler,
                    repository = userRepository,
                    username = usernameEditText.text.toString(),
                    password = passwordEditText.text.toString(),
                    email = emailEditText.text.toString()
                ).start()

            } else {
                showError("Неверные данные")
            }
        }

        view.findViewById<Button>(R.id.login).setOnClickListener {

            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }

    private fun isValidInfo() = usernameEditText.text.isNotEmpty() && emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()

    private fun showError(errorMessage: String) {

        Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}

class RegistrationThread(

    private val handler: Handler,
    private val repository: UserRepository,
    private val username: String,
    private val email: String,
    private val password: String

) : Thread() {

    override fun run() {

        val msg = try {

            val user = repository.registration(username, email, password)
            handler.obtainMessage(RegistrationFragment.REGISTRATION_SUCCESS, user)
        } catch (e: Exception) {

            handler.obtainMessage(RegistrationFragment.REGISTRATION_FAILED, e.message)
        }

        handler.sendMessage(msg)
    }
}