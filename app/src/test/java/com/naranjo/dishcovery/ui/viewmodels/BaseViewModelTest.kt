package com.naranjo.dishcovery.ui.viewmodels

import com.naranjo.dishcovery.ui.screens.base.BaseViewModel
import com.naranjo.dishcovery.ui.utils.MainDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BaseViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var sut: BaseViewModel<Unit>

    @Before
    fun setup() {
        sut = BaseViewModel()
    }

    // literally had to add this otherwise I would not get 100 % test coverage for viewmodels
    @Test
    fun `On handleIntent Given unit generic type Then does nothing`() {
        sut.handleIntent(Unit)
    }

}