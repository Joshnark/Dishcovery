package com.naranjo.dishcovery.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.naranjo.dishcovery.ui.screens.base.BaseFragment
import com.naranjo.dishcovery.ui.screens.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: BaseFragment<Void>() {

    override val viewModel: MainViewModel by viewModels()

    override fun setContent(): @Composable () -> Unit = {
        MainScreen()
    }

}