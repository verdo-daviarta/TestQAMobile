package com.example.testqamobile.eraspace

import androidx.test.uiautomator.By
import org.junit.Assert.assertTrue

internal class LoginScreen(private val app: EraspaceApp) {
    fun open() {
        app.requireElement(
            "tombol Masuk di beranda (pastikan Eraspace sudah logout)",
            By.res("com.eraspace.app.home", "btnLogin")
        ).click()
        app.requireElement("form login", usernameInput)
    }

    fun enterUsername(username: String) {
        val field = app.requireElement("kolom nomor handphone atau email", usernameInput)
        field.setText(username)
        assertTrue("Username tidak terisi sesuai data uji", field.text == username)
    }

    fun enterPassword(password: String) {
        val field = app.requireElement("kolom password", passwordInput)
        field.setText(password)
        val entered = field.text.orEmpty()
        val isMasked = entered.length == password.length && entered.all { it in "•●*" }
        assertTrue("Password tidak terisi sesuai data uji", entered == password || isMasked)
    }

    fun submit() {
        app.requireElement("tombol Masuk pada form login", By.res(MEMBERSHIP, "btnLogin").enabled(true))
            .click()
    }

    fun assertRejected() {
        app.requireElement(
            "pesan email atau nomor handphone belum terdaftar",
            By.res(MEMBERSHIP, "tvError").text("Email atau No Handphone belum terdaftar")
        )
        app.requireElement("form login tetap terbuka setelah ditolak", usernameInput)
    }

    companion object {
        private const val MEMBERSHIP = "com.eraspace.app.membership"
        private val usernameInput = By.res(MEMBERSHIP, "edtPhoneNumber")
        private val passwordInput = By.res(MEMBERSHIP, "edtPassword")
    }
}
