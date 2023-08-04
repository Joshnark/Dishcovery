package com.naranjo.dishcovery.screens.ui.fragment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.naranjo.dishcovery.R
import com.naranjo.dishcovery.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SplashFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavController

    @Before
    fun setup() {
        composeTestRule.activityRule.scenario.onActivity {
            navController = findNavController(it, R.id.nav_host_fragment).apply {
                navigate(R.id.splashFragment)
            }
        }
    }

    @Test
    fun whenSplashNavigationButtonClicked_goToMain() {
        val button = composeTestRule.onNodeWithTag("splashNavigationButton")

        button.assertIsDisplayed()

        button.performClick()

        assert(navController.currentDestination?.id == R.id.mainFragment)
    }

}