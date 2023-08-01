package com.naranjo.dishcovery.ui.screens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {
    private val _navigation = MutableSharedFlow<NavigationCommand>()
    val navigation: SharedFlow<NavigationCommand> = _navigation

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