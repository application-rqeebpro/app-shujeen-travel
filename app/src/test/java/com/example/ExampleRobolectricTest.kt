package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("وكالة شجين للسفريات", appName)
  }

  @Test
  fun `verify arabic language uses RTL and english uses LTR`() {
    val ar = com.example.ui.locale.AppLanguage.ARABIC
    val en = com.example.ui.locale.AppLanguage.ENGLISH

    assertEquals(androidx.compose.ui.unit.LayoutDirection.Rtl, ar.layoutDirection)
    assertTrue(ar.isRtl)

    assertEquals(androidx.compose.ui.unit.LayoutDirection.Ltr, en.layoutDirection)
    assertFalse(en.isRtl)
  }

  @Test
  fun `verify localized dictionaries match selected language`() {
    val arStrings = com.example.ui.locale.getStrings(com.example.ui.locale.AppLanguage.ARABIC)
    val enStrings = com.example.ui.locale.getStrings(com.example.ui.locale.AppLanguage.ENGLISH)

    assertEquals("وكالة شجين", arStrings.agencyName)
    assertEquals("Shajeen Agency", enStrings.agencyName)

    assertEquals("الرئيسية", arStrings.navHome)
    assertEquals("Home", enStrings.navHome)

    assertEquals("حجوزاتي", arStrings.navBookings)
    assertEquals("My Bookings", enStrings.navBookings)
  }
}
