package com.example.testqamobile.eraspace

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Condition
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2

internal class EraspaceApp {
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val device = UiDevice.getInstance(instrumentation)

    fun launch() {
        val context = instrumentation.targetContext
        val intent = context.packageManager.getLaunchIntentForPackage(PACKAGE_NAME)
            ?: throw AssertionError("Instal Eraspace dari Play Store di perangkat pengujian")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        requireElement("jendela Eraspace", By.pkg(PACKAGE_NAME).depth(0))
    }

    fun requireElement(description: String, vararg selectors: BySelector): UiObject2 =
        device.wait(Condition { device: UiDevice -> selectors.firstNotNullOfOrNull(device::findObject) }, TIMEOUT_MS)
            ?: throw AssertionError("Elemen tidak ditemukan dalam ${TIMEOUT_MS / 1000} detik: $description")

    companion object {
        private const val PACKAGE_NAME = "com.eraspace.app"
        private const val TIMEOUT_MS = 15_000L
    }
}
