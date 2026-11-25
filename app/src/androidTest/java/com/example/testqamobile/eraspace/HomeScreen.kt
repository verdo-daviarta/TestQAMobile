package com.example.testqamobile.eraspace

import androidx.test.uiautomator.By
import org.junit.Assert.assertTrue
import java.util.regex.Pattern

internal class HomeScreen(private val app: EraspaceApp) {
    fun assertSignedIn() {
        val activeHome = By.res(HOME, "navigationHome").selected(true)
        val verificationReminder = By.res(APP, "containerDialog")
            .hasDescendant(By.res(APP, "tvTitle").text(Pattern.compile("(?i).*verifikasi.*")))
        val next = app.requireElement("tab Home atau pengingat verifikasi", verificationReminder, activeHome)
        if (next.resourceName == "$APP:id/containerDialog") {
            app.requireElement(
                "tombol tutup pengingat verifikasi",
                By.res(APP, "btnClose").hasAncestor(verificationReminder)
            ).click()
        }
        app.requireElement("tab Home aktif setelah login", activeHome)
        app.requireElement(
            "saldo poin akun di beranda",
            By.res(HOME, "tvPoint").text(Pattern.compile(".*\\d.*"))
        )
    }

    fun assertMenuIsPopulated() {
        val menuItem = By.res(APP, "tvLabel").text(Pattern.compile(".*\\S.*"))
        val menu = app.requireElement(
            "daftar menu kategori di beranda",
            By.res(APP, "rvIconsLabelMenu")
                .hasAncestor(By.res(HOME, "customCategoryMenuContent"))
                .hasDescendant(menuItem)
        )
        val labels = menu.findObjects(menuItem).mapNotNull { it.text }.distinct()
        assertTrue("Daftar menu kategori harus menampilkan minimal dua item", labels.size >= 2)
    }

    fun openAccountAndAssertName() {
        app.requireElement("tab Akun", By.res(HOME, "navigationProfile")).click()
        app.requireElement(
            "nama akun di tab Akun",
            By.res(APP, "tvName")
                .hasAncestor(By.res(HOME, "profileHeaderContainer"))
                .text(Pattern.compile(".*\\p{L}.*"))
        )
    }

    fun openPointsAndAssertHistory() {
        app.requireElement("tab Home", By.res(HOME, "navigationHome")).click()
        app.requireElement("tombol Cek Poin di beranda", By.res(HOME, "tvCheckPoint")).click()
        app.requireElement("judul Riwayat Poin", By.res(MEMBERSHIP, "tvLabelPointHistory"))
        app.requireElement(
            "riwayat transaksi atau pesan belum memiliki riwayat poin",
            By.res(MEMBERSHIP, "rvHistoryPoints")
                .hasDescendant(By.res(MEMBERSHIP, "tvPointHistoryPoint").text(Pattern.compile(".*\\d.*"))),
            By.res(APP, "tvTitle").text("Belum memiliki riwayat poin")
                .hasAncestor(By.res(MEMBERSHIP, "msvHistoryPoints"))
        )
    }

    companion object {
        private const val APP = "com.eraspace.app"
        private const val HOME = "com.eraspace.app.home"
        private const val MEMBERSHIP = "com.eraspace.app.membership"
    }
}
