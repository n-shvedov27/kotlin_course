package com.shvedov.livinir.presentation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shvedov.livinir.R
import com.shvedov.livinir.data.network.entity.Post
import com.shvedov.livinir.presentation.create_post.CreatePostFragment
import com.shvedov.livinir.presentation.login.LoginFragment
import com.shvedov.livinir.presentation.post_info.PostInfoFragment
import com.shvedov.livinir.presentation.post_list.PostListFragment
import com.shvedov.livinir.presentation.registration.RegistrationFragment

class MainActivity : AppCompatActivity(), AuthNavigation, AppNavigation {

    companion object {
        private const val REGISTRATION_FRAGMENT_TAG = "auth_fragment"
        private const val LOGIN_FRAGMENT_TAG = "login_fragment"
        private const val USERS_LIST_TAG = "users_list_fragment"
        private const val CREATE_POST_FRAGMENT_TAG = "create_post_fragment"
        private const val POST_INFO_FRAGMENT_TAG = "post_info_fragment"
        const val USER_KEY = "shared_pref_user_key"
        private const val TAG = "MY_DEBUG"
        private const val SCREEN_ID = "screen_id"
        private const val LOGIN_SCREEN = 0
        private const val REGISTRATION_SCREEN = 1
        private const val LIST_SCREEN = 2
        private const val CREATE_POST_SCREEN = 3
        private const val POST_INFO_SCREEN = 4
    }

    private var currentScreen = LIST_SCREEN

    private lateinit var registrationFragment: RegistrationFragment
    private lateinit var loginFragment: LoginFragment
    private lateinit var postListFragment: PostListFragment
    private lateinit var createPostFragment: CreatePostFragment
    private lateinit var postInfoFragment: PostInfoFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val a=supportFragmentManager.fragments

        if (!isAuthorized()) {

            when (savedInstanceState?.get(SCREEN_ID)) {
                REGISTRATION_SCREEN, null -> showRegistrationFragment(savedInstanceState)
                LOGIN_SCREEN -> showLoginFragment(savedInstanceState)
            }
        } else {

            when(savedInstanceState?.get(SCREEN_ID)) {
                LIST_SCREEN, null -> showPostListFragment(savedInstanceState)
                CREATE_POST_SCREEN -> showCreatePostFragment(savedInstanceState)
                POST_INFO_SCREEN -> showPostInfoFragment(savedInstanceState)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SCREEN_ID, currentScreen)
    }

    override fun openLoginScreen() {
        showLoginFragment()
    }

    override fun openRegistrationScreen() {
        showRegistrationFragment()
    }

    override fun onAuthSuccess(userId: String) {

        getPreferences(Context.MODE_PRIVATE).edit()
            .putString(USER_KEY, userId)
            .apply()

        (application as App).state = AppState.AUTHORIZED

        showPostListFragment()
    }

    private fun showLoginFragment(savedInstanceState: Bundle? = null) {

        if (savedInstanceState == null) {

            loginFragment = LoginFragment()

            supportFragmentManager.beginTransaction()
                .replace(R.id.root_container, loginFragment, LOGIN_FRAGMENT_TAG)
                .commit()
        } else {

            loginFragment = supportFragmentManager.findFragmentByTag(LOGIN_FRAGMENT_TAG) as LoginFragment
        }

        currentScreen = LOGIN_SCREEN
        loginFragment.attachToHandler(this)
    }


    private fun showRegistrationFragment(savedInstanceState: Bundle?=null) {

        if (savedInstanceState == null) {

            registrationFragment = RegistrationFragment()

            supportFragmentManager.beginTransaction()
                .replace(R.id.root_container, registrationFragment, REGISTRATION_FRAGMENT_TAG)
                .commit()
        } else {

            registrationFragment = supportFragmentManager.findFragmentByTag(
                REGISTRATION_FRAGMENT_TAG
            ) as RegistrationFragment
        }

        currentScreen = REGISTRATION_SCREEN
        registrationFragment.attachToHandler(this)
    }

    private fun showPostListFragment(savedInstanceState: Bundle? = null) {
        if (savedInstanceState == null) {

            postListFragment = PostListFragment()

            supportFragmentManager.beginTransaction()
                .replace(R.id.root_container, postListFragment, USERS_LIST_TAG)
                .commit()
        } else {

            postListFragment = supportFragmentManager.findFragmentByTag(USERS_LIST_TAG) as PostListFragment
        }

        currentScreen = LIST_SCREEN
        postListFragment.attachToHandler(this)
    }

    private fun showCreatePostFragment(savedInstanceState: Bundle? = null) {
        if (savedInstanceState == null) {

            createPostFragment = CreatePostFragment()

            supportFragmentManager.beginTransaction()
                .replace(R.id.root_container, createPostFragment, CREATE_POST_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit()
        } else {

            createPostFragment = supportFragmentManager.findFragmentByTag(
                CREATE_POST_FRAGMENT_TAG
            ) as CreatePostFragment
        }

        currentScreen = CREATE_POST_SCREEN
        createPostFragment.attachToHandler(this)
    }

    private fun showPostInfoFragment(savedInstanceState: Bundle? = null, post: Post?=null) {
        if (savedInstanceState == null) {

            postInfoFragment = PostInfoFragment.newInstance(
                username = post!!.author.username,
                label = post.title,
                text = post.text
            )

            supportFragmentManager.beginTransaction()
                .replace(R.id.root_container, postInfoFragment, POST_INFO_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit()
        } else {

            postInfoFragment = supportFragmentManager.findFragmentByTag(POST_INFO_FRAGMENT_TAG) as PostInfoFragment
        }

        currentScreen = POST_INFO_SCREEN
    }

    private fun isAuthorized()= (application as App).isAuthorized

    override fun openPostListScreen() {
        showPostListFragment()
    }

    override fun openCreatePostScreen() {
        showCreatePostFragment()
    }

    override fun openPostInfo(post: Post) {
        showPostInfoFragment(post = post)
    }
}