package com.naranjo.dishcovery.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.naranjo.dishcovery.ui.screens.base.BaseFragment

class MainFragment: BaseFragment() {

    override fun setContent(): @Composable () -> Unit = {
        MainScreen()
    }

}