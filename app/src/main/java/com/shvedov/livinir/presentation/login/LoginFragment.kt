package com.shvedov.livinir.presentation.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shvedov.livinir.R
import com.shvedov.livinir.data.repository.UserRepository
import com.shvedov.livinir.data.repository.UserRepositoryImpl
import com.shvedov.livinir.presentation.AuthService
import com.shvedov.livinir.presentation.di.DaggerAppComponent
import com.shvedov.livinir.presentation.entity.User
import com.shvedov.livinir.presentation.extension.requireActivityAs
import com.shvedov.livinir.presentation.post_list.PostListFragmentDirections
import com.shvedov.livinir.utils.injectViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var passwordEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var viewModel: LoginViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.create().plusLoginComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)
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