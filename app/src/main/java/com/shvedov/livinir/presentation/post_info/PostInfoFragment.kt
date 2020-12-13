package com.shvedov.livinir.presentation.post_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shvedov.livinir.R

class PostInfoFragment : Fragment() {

    private var username: String? = null
    private var text: String? = null
    private var label: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = PostInfoFragmentArgs.fromBundle(requireArguments())

        username = args.username
        text = args.text
        label = args.label
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
}