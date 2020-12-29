package com.shvedov.livinir.screen

import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.text.KButton
import com.kaspersky.kaspresso.screens.KScreen
import com.shvedov.livinir.R
import com.shvedov.livinir.presentation.screen.registration.RegistrationFragment

object RegistrationScreen : KScreen<RegistrationScreen>() {
    override val layoutId: Int?
        get() = R.layout.fragment_registration
    override val viewClass: Class<*>?
        get() = RegistrationFragment::class.java

    val nameEditText = KEditText { withId(R.id.name) }
    val emailEditText = KEditText { withId(R.id.email) }
    val passwordEditText = KEditText { withId(R.id.password) }
    val registrationButton = KButton { withId(R.id.registration) }
    val loginButton = KButton { withId(R.id.login) }
}