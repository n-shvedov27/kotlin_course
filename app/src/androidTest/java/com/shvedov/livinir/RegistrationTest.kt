package com.shvedov.livinir

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.shvedov.livinir.screen.RegistrationScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationTest : TestCase() {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun visibilityTest() = run {

        step("Проверить видимость всех полей") {

            RegistrationScreen {
                nameEditText.isVisible()
                emailEditText.isVisible()
                passwordEditText.isVisible()
                registrationButton.isVisible()
                loginButton.isVisible()
            }
        }
    }
}