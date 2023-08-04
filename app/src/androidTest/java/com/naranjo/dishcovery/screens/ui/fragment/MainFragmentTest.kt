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
class MainFragmentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var navController: NavController

    @Before
    fun setup() {
        composeTestRule.activityRule.scenario.onActivity {
            navController = findNavController(it, R.id.nav_host_fragment).apply {
                navigate(R.id.mainFragment)
            }
        }
    }

    @Test
    fun onSearchTabSelected_searchScreenIsDisplayed() {
        val tab = composeTestRule.onNodeWithTag("MainFragmentTab0")
        val searchScreen = composeTestRule.onNodeWithTag("SearchScreen")

        tab.assertIsDisplayed()

        tab.performClick()

        searchScreen.assertIsDisplayed()
    }

    @Test
    fun onFavoriteTabSelected_favoriteScreenIsDisplayed() {
        val tab = composeTestRule.onNodeWithTag("MainFragmentTab2")
        val favoriteScreen = composeTestRule.onNodeWithTag("FavoriteScreen")

        tab.assertIsDisplayed()

        tab.performClick()

        favoriteScreen.assertIsDisplayed()
    }

    @Test
    fun onSearchTabSelectedAndHomeTabSelected_homeScreenIsDisplayed() {
        val searchTab = composeTestRule.onNodeWithTag("MainFragmentTab0")
        val homeTab = composeTestRule.onNodeWithTag("MainFragmentTab1")
        val searchScreen = composeTestRule.onNodeWithTag("SearchScreen")
        val homeScreen = composeTestRule.onNodeWithTag("HomeScreen")

        searchTab.assertIsDisplayed()
        homeTab.assertIsDisplayed()

        searchTab.performClick()

        searchScreen.assertIsDisplayed()

        homeTab.performClick()

        homeScreen.assertIsDisplayed()
    }
}