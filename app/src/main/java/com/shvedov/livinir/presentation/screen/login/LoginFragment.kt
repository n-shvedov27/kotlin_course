package com.shvedov.livinir.presentation.screen.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.shvedov.livinir.R
import com.shvedov.livinir.di.app.DaggerAppComponent
import com.shvedov.livinir.presentation.AuthService
import com.shvedov.livinir.presentation.core_ui.BaseFragment
import com.shvedov.livinir.presentation.entity.User
import com.shvedov.livinir.presentation.extension.requireActivityAs
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginFragment : BaseFragment<LoginViewModel>() {

    override val layoutResId = R.layout.fragment_login

    private lateinit var passwordEditText: EditText
    private lateinit var emailEditText: EditText

    override val viewModel by injectViewModel<LoginViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.create().plusLoginComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText = view.findViewById(R.id.login_fragment_email)
        passwordEditText = view.findViewById(R.id.login_fragment_password)

        view.findViewById<Button>(R.id.login_fragment_login).setOnClickListener {

            if (isValidInfo()) {

                login(
                    email = emailEditText.text.toString(),
                    password = passwordEditText.text.toString()
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {

                            requireActivityAs<AuthService>().onAuthSuccess(it.id)
                            findNavController().navigate(R.id.action_loginFragment_to_postListFragment)
                        },
                        { showError(it.message ?: it.toString()) }
                    )
            } else {

                Toast.makeText(requireContext(), "Неверные данные", Toast.LENGTH_SHORT).show()

            }
        }

        view.findViewById<TextView>(R.id.login_fragment_registration).setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun showError(errorMessage: String) {

        Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun login(email: String, password: String) = Single.create<User> {

        kotlin.runCatching {

            val user = viewModel.login(email, password)
            it.onSuccess(user)
        }.onFailure { e -> it.onError(e) }
    }

    private fun isValidInfo() = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
}