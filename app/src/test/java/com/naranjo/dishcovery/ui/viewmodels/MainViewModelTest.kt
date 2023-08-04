package com.naranjo.dishcovery.ui.viewmodels

import app.cash.turbine.test
import com.naranjo.dishcovery.mocks.fakeRecipe
import com.naranjo.dishcovery.ui.screens.base.NavigationCommand
import com.naranjo.dishcovery.ui.screens.main.MainFragmentDirections
import com.naranjo.dishcovery.ui.screens.main.MainViewModel
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
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var sut: MainViewModel

    @Before
    fun setup() {
        sut = MainViewModel()
    }

    @Test
    fun `On navigate Given success Then navigation is emitted`() = runBlocking {
        sut.navigation.test(5.seconds) {
            sut.navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(fakeRecipe.id))
            Assert.assertEquals(awaitItem(), NavigationCommand.ToDirection(MainFragmentDirections.actionMainFragmentToDetailFragment(fakeRecipe.id)))
        }
    }

    @Test
    fun `On navigate back Given success Then navigation back is emitted`() = runBlocking {
        sut.navigation.test(5.seconds) {
            sut.navigateBack()
            Assert.assertEquals(awaitItem(), NavigationCommand.Back)
        }
    }

    @Test
    fun `On pager change Given success Then pager flow value is emitted`() = runBlocking {
        sut.pagerChange.test(5.seconds) {
            sut.changePagerToPage(0)
            Assert.assertEquals(awaitItem(), 0)
        }
    }

}