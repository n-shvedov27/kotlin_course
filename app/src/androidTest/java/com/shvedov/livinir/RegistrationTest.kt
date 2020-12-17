package com.shvedov.livinir

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.shvedov.livinir.presentation.MainActivity
import com.shvedov.livinir.rule.WebServerMockRule
import com.shvedov.livinir.screen.RegistrationScreen
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationTest : TestCase() {

    @get:Rule
    val rulesChain: RuleChain = RuleChain.outerRule(WebServerMockRule()).around(ActivityTestRule(MainActivity::class.java))

    @Test
    fun visibilityTest() = run {

        step("Проверить видимость всех полей") {

            RegistrationScreen {
                nameEditText.isVisible()
                nameEditText.typeText("qwe")
                emailEditText.isVisible()
                emailEditText.typeText("qwe")
                passwordEditText.isVisible()
                passwordEditText.typeText("qwe")
                registrationButton.isVisible()
                registrationButton.click()
            }
        }
    }
}