package com.shvedov.livinir.presentation.screen.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.shvedov.livinir.R
import com.shvedov.livinir.presentation.Authorized
import com.shvedov.livinir.presentation.MainActivity

class SplashFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        super.onResume()

        val userId = requireActivity().getPreferences(Context.MODE_PRIVATE).getString(MainActivity.USER_KEY, null)

        if (userId == null) {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        } else {
            (requireActivity() as MainActivity).appState = Authorized(userId)
            findNavController().navigate(R.id.action_splashFragment_to_postListFragment)
        }
    }
}