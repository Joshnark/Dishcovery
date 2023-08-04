package com.naranjo.dishcovery.ui.screens.main

import androidx.lifecycle.viewModelScope
import com.naranjo.dishcovery.ui.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BaseViewModel<Void>() {

    private val _pagerChange = MutableSharedFlow<Int>()
    val pagerChange: SharedFlow<Int> = _pagerChange

    fun changePagerToPage(page: Int) {
        viewModelScope.launch {
            _pagerChange.emit(page)
        }
    }

}