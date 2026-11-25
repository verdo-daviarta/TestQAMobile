package com.example.testqamobile.eraspace

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assume.assumeTrue
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class EraspaceLoginTest {
    @Test
    fun tc11_invalidCredentialsShowLoginError() {
        val app = EraspaceApp()
        val login = LoginScreen(app)
        app.launch()
        login.open()
        login.enterUsername("qa-eraspace-invalid@example.com")
        login.enterPassword("InvalidPassword123!")
        login.submit()

        login.assertRejected()
    }

    @Test
    fun tc12_validCredentialsOpenSignedInHome() {
        val arguments = InstrumentationRegistry.getArguments()
        val username = arguments.getString("eraspaceUsername").orEmpty()
        val password = arguments.getString("eraspacePassword").orEmpty()
        assumeTrue(
            "TC 1.2 memerlukan kredensial di eraspace-test.properties.",
            username.isNotBlank() || password.isNotBlank()
        )
        require(username.isNotBlank() && password.isNotBlank()) {
            "Isi eraspaceUsername dan eraspacePassword."
        }

        val app = EraspaceApp()
        val login = LoginScreen(app)
        app.launch()
        login.open()
        login.enterUsername(username)
        login.enterPassword(password)
        login.submit()

        val home = HomeScreen(app)
        home.assertSignedIn()
        home.assertMenuIsPopulated()
        home.openAccountAndAssertName()
        home.openPointsAndAssertHistory()
    }
}
