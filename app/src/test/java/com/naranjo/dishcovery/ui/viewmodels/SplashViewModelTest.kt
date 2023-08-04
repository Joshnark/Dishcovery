package com.naranjo.dishcovery.ui.viewmodels

import app.cash.turbine.test
import com.naranjo.dishcovery.ui.screens.base.NavigationCommand
import com.naranjo.dishcovery.ui.screens.splash.SplashFragmentDirections
import com.naranjo.dishcovery.ui.screens.splash.SplashViewModel
import com.naranjo.dishcovery.ui.utils.MainDispatcherRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var sut: SplashViewModel

    @Before
    fun setup() {
        sut = SplashViewModel()
    }

    @Test
    fun `On navigate Given success Then navigation is emitted`() = runBlocking {
        sut.navigation.test(5.seconds) {
            sut.navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
            Assert.assertEquals(awaitItem(), NavigationCommand.ToDirection(SplashFragmentDirections.actionSplashFragmentToMainFragment()))
        }
    }

    @Test
    fun `On navigate back Given success Then navigation back is emitted`() = runBlocking {
        sut.navigation.test(5.seconds) {
            sut.navigateBack()
            Assert.assertEquals(awaitItem(), NavigationCommand.Back)
        }
    }

}