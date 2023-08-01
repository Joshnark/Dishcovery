package com.naranjo.dishcovery.ui.screens.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import com.naranjo.dishcovery.ui.theme.DishCoveryTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment: Fragment() {

    open val viewModel: BaseViewModel? = null

    abstract fun setContent(): @Composable () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                DishCoveryTheme {
                    setContent().invoke()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel?.navigation?.collectLatest { command ->
                handleNavigation(view, command)
            }
        }
    }

    private fun handleNavigation(view: View, navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> findNavController(view).navigate(navCommand.directions)
            is NavigationCommand.Back -> findNavController(view).navigateUp()
            else -> {}
        }
    }

}