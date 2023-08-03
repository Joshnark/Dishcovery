package com.naranjo.dishcovery.ui.screens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeIntent
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeState
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

abstract class BaseViewModel<I>: ViewModel() {
    val intent = Channel<I>(Channel.UNLIMITED)

    private val _navigation = MutableSharedFlow<NavigationCommand>()
    val navigation: SharedFlow<NavigationCommand> = _navigation

    private var currentJob: Job? = null

    init {
        observeIntent()
    }

    private fun observeIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect { intent ->
                currentJob = handleIntent(intent)
            }
        }
    }

    open fun handleIntent(intent: I): Job? = null

    fun cancelCurrentJob() {
        currentJob?.cancel(CancellationException())
    }

    fun navigate(navDirections: NavDirections) {
        viewModelScope.launch {
            _navigation.emit(NavigationCommand.ToDirection(navDirections))
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigation.emit(NavigationCommand.Back)
        }
    }
}