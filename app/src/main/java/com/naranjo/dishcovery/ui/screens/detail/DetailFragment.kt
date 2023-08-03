package com.naranjo.dishcovery.ui.screens.detail

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.naranjo.dishcovery.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment: BaseFragment<DetailIntent>() {

    private val args: DetailFragmentArgs by navArgs()

    override val viewModel: DetailViewModel by viewModels()

    override fun setContent(): @Composable () -> Unit = {
        DetailScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainScope().launch {
            viewModel.intent.send(DetailIntent.GetRecipe(args.recipeId))
        }
    }

}