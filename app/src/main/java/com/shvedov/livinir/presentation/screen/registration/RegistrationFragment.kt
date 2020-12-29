package com.shvedov.livinir.presentation.screen.registration

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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

class RegistrationFragment : BaseFragment<RegistrationViewModel>() {

    override val viewModel: RegistrationViewModel by injectViewModel()
    override val layoutResId = R.layout.fragment_registration

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.create().registrationComponent().inject(this)
    }

    private lateinit var passwordEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText

    private fun registration(username: String, email: String, password: String) = Single.create<User> {

        kotlin.runCatching {

            val user = viewModel.registration(username, email, password)
            it.onSuccess(user)

        }.onFailure { e -> it.onError(e) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameEditText = view.findViewById(R.id.name)
        emailEditText = view.findViewById(R.id.email)
        passwordEditText = view.findViewById(R.id.password)

        view.findViewById<Button>(R.id.registration).setOnClickListener {

            if (isValidInfo()) {
                
                registration(
                    username = usernameEditText.text.toString(),
                    password = passwordEditText.text.toString(),
                    email = emailEditText.text.toString()
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            requireActivityAs<AuthService>().onAuthSuccess(it.id)
                            findNavController().navigate(R.id.action_registrationFragment_to_postListFragment)

                        },
                        {
                            showError(it.message ?: it.toString())
                        }
                    )

            } else {
                showError("Неверные данные")
            }
        }

        view.findViewById<View>(R.id.login).setOnClickListener {

            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }

    private fun isValidInfo() = usernameEditText.text.isNotEmpty() && emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()

    private fun showError(errorMessage: String) {

        Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}