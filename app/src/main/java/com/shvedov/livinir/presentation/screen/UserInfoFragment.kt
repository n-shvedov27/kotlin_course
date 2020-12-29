package com.shvedov.livinir.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.shvedov.livinir.R
import com.shvedov.livinir.presentation.AuthService
import com.shvedov.livinir.presentation.extension.requireActivityAs
import com.shvedov.livinir.presentation.screen.post_info.PostInfoFragmentArgs

class UserInfoFragment : Fragment() {

    private lateinit var currentUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentUserId = UserInfoFragmentArgs.fromBundle(requireArguments()).currentUserId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<View>(R.id.user_info_fragment_logout_btn).setOnClickListener {
            requireActivityAs<AuthService>().onLogout()
        }
    }
}