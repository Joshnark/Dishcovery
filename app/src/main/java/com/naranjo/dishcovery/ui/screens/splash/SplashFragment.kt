package com.naranjo.dishcovery.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import com.naranjo.dishcovery.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment: BaseFragment() {

    override val viewModel: SplashViewModel by viewModels()

    override fun setContent(): @Composable () -> Unit = {
        SplashScreen()
    }

}