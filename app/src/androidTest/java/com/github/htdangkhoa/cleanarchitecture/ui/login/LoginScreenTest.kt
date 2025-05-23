package com.github.htdangkhoa.cleanarchitecture.ui.login

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_displaysAllElements() {
        composeTestRule.setContent {
            LoginScreen(
                phoneNumberState = "",
                passwordState = "",
                isLoading = false,
                onPhoneNumberChange = {},
                onPasswordChange = {},
                onLoginClick = {}
            )
        }

        composeTestRule.onNodeWithText("Phone Number").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }

    @Test
    fun phoneNumberField_updatesStateCorrectly() {
        var phoneNumber = ""
        composeTestRule.setContent {
            LoginScreen(
                phoneNumberState = phoneNumber,
                passwordState = "",
                isLoading = false,
                onPhoneNumberChange = { phoneNumber = it },
                onPasswordChange = {},
                onLoginClick = {}
            )
        }

        composeTestRule.onNodeWithText("Phone Number").performTextInput("12345")
        assert(phoneNumber == "12345")
    }

    @Test
    fun passwordField_updatesStateCorrectly() {
        var password = ""
        composeTestRule.setContent {
            LoginScreen(
                phoneNumberState = "",
                passwordState = password,
                isLoading = false,
                onPhoneNumberChange = {},
                onPasswordChange = { password = it },
                onLoginClick = {}
            )
        }

        composeTestRule.onNodeWithText("Password").performTextInput("secret")
        assert(password == "secret")
    }

    @Test
    fun loginButton_callsOnLoginClick() {
        val onLoginClickMock = mock<() -> Unit>()
        composeTestRule.setContent {
            LoginScreen(
                phoneNumberState = "123",
                passwordState = "pass",
                isLoading = false,
                onPhoneNumberChange = {},
                onPasswordChange = {},
                onLoginClick = onLoginClickMock
            )
        }

        composeTestRule.onNodeWithText("Login").performClick()
        verify(onLoginClickMock).invoke()
    }

    @Test
    fun progressBar_isVisibleWhenLoading() {
        composeTestRule.setContent {
            LoginScreen(
                phoneNumberState = "",
                passwordState = "",
                isLoading = true,
                onPhoneNumberChange = {},
                onPasswordChange = {},
                onLoginClick = {}
            )
        }

        // We can't directly test for the CircularProgressIndicator without a test tag
        // However, we can check if the Login button is disabled, which is an indirect way
        composeTestRule.onNodeWithText("Login").assertIsNotEnabled()
    }

    @Test
    fun progressBar_isNotVisibleWhenNotLoading() {
        composeTestRule.setContent {
            LoginScreen(
                phoneNumberState = "",
                passwordState = "",
                isLoading = false,
                onPhoneNumberChange = {},
                onPasswordChange = {},
                onLoginClick = {}
            )
        }
        // Check that the login button is enabled
        composeTestRule.onNodeWithText("Login").assertIsEnabled()
    }
}
