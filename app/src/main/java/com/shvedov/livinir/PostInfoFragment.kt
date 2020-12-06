package com.shvedov.livinir

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

private const val ARG_PARAM_USERNAME = "user_name"
private const val ARG_PARAM_TEXT = "text"
private const val ARG_PARAM_LABEL = "label"

class PostInfoFragment : Fragment() {

    private var username: String? = null
    private var text: String? = null
    private var label: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM_USERNAME)
            text = it.getString(ARG_PARAM_TEXT)
            label = it.getString(ARG_PARAM_LABEL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.post_info_fragment_post_title).text = label
        view.findViewById<TextView>(R.id.post_info_fragment_post_text).text = text
        view.findViewById<TextView>(R.id.post_info_fragment_post_author).text = username
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String, label: String, text: String) =
            PostInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_LABEL, label)
                    putString(ARG_PARAM_TEXT, text)
                    putString(ARG_PARAM_USERNAME, username)
                }
            }
    }
}