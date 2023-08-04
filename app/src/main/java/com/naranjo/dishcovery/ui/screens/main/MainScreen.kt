package com.naranjo.dishcovery.ui.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naranjo.dishcovery.R
import com.naranjo.dishcovery.ui.extensions.responsive
import com.naranjo.dishcovery.ui.screens.main.models.PageData
import com.naranjo.dishcovery.ui.screens.main.pages.favorite.FavoritesIntent
import com.naranjo.dishcovery.ui.screens.main.pages.favorite.FavoritesScreen
import com.naranjo.dishcovery.ui.screens.main.pages.favorite.FavoritesViewModel
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeIntent
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeScreen
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeViewModel
import com.naranjo.dishcovery.ui.screens.main.pages.search.SearchIntent
import com.naranjo.dishcovery.ui.screens.main.pages.search.SearchScreen
import com.naranjo.dishcovery.ui.screens.main.pages.search.SearchViewModel
import com.naranjo.dishcovery.ui.theme.NANO
import com.naranjo.dishcovery.ui.theme.TINY
import com.naranjo.dishcovery.ui.views.SmallSpacer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val ICON_SIZE = 20

private const val SEARCH_SCREEN_INDEX = 0
private const val HOME_SCREEN_INDEX = 1
private const val FAVORITES_SCREEN_INDEX = 2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {

    val pageData = mapOf(
        SEARCH_SCREEN_INDEX to PageData(
            outlineIcon = R.drawable.ic_search,
            selectedIcon = R.drawable.ic_search_selected,
            screen = { SearchScreen(
                onRecipeTap = { recipe ->
                    viewModel.navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(recipe.id))
                }
            ) }
        ),
        HOME_SCREEN_INDEX to PageData(
            outlineIcon = R.drawable.ic_home,
            selectedIcon = R.drawable.ic_home_selected,
            screen = {
                HomeScreen(
                    onRecipeTap = { recipe ->
                        viewModel.navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(recipe.id))
                    }
                )
            }
        ),
        FAVORITES_SCREEN_INDEX to PageData(
            outlineIcon = R.drawable.ic_like,
            selectedIcon = R.drawable.ic_like_selected,
            screen = { FavoritesScreen(
                onRecipeTap = { recipe ->
                    viewModel.navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(recipe.id))
                }
            ) }
        )
    )

    val pagerState = rememberPagerState(
        pageCount = {
            pageData.size
        },
        initialPage = HOME_SCREEN_INDEX
    )

    LaunchedEffect(Unit) {
        viewModel.pagerChange.collect { page ->
            if (page in 0 ..< pagerState.pageCount) {
                pagerState.animateScrollToPage(page)
            }
        }
    }

    Scaffold(
        bottomBar = {
            TabBar(
                pagerState = pagerState,
                pageData = pageData
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .responsive()
                    .padding(padding)
                    .testTag(stringResource(id = R.string.main_pager_tag)),
                userScrollEnabled = false
            ) { pageIndex ->
                pageData[pageIndex]?.screen?.invoke()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabBar(
    pagerState: PagerState,
    pageData: Map<Int, PageData>,
    homeViewModel: HomeViewModel = viewModel(),
    searchViewModel: SearchViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        divider = {
            Spacer(modifier = Modifier.height(TINY.dp))
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.surface,
            )
            .shadow(
                elevation = TINY.dp
            )
    ) {
        pageData.forEach { (index, value) ->
            Tab(
                modifier = Modifier.testTag(stringResource(R.string.main_tab_tag, index)),
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        updateScreens(index, scope, homeViewModel, searchViewModel, favoritesViewModel)
                        pagerState.animateScrollToPage(index)
                    }
                },
                icon = {
                    val colorFilter =
                        if (pagerState.currentPage == index) {
                            ColorFilter.tint(MaterialTheme.colorScheme.primary)
                        } else {
                            ColorFilter.tint(MaterialTheme.colorScheme.outline)
                        }

                    val icon =
                        if (pagerState.currentPage == index) {
                            value.selectedIcon
                        } else {
                            value.outlineIcon
                        }

                    Column {
                        SmallSpacer()
                        Image(
                            painter = painterResource(id = icon),
                            modifier = Modifier.size(ICON_SIZE.dp),
                            colorFilter = colorFilter,
                            contentDescription = null
                        )
                        SmallSpacer()
                    }
                })
        }
    }
}

private fun updateScreens(
    index: Int,
    scope: CoroutineScope,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    scope.launch {
        when(index) {
            SEARCH_SCREEN_INDEX -> searchViewModel.intent.send(SearchIntent.SearchRecipes(load = false))
            HOME_SCREEN_INDEX -> homeViewModel.intent.send(HomeIntent.GetRecipes(load = false))
            FAVORITES_SCREEN_INDEX -> favoritesViewModel.intent.send(FavoritesIntent.GetFavorites(load = false))
        }
    }
}

@Preview
@Composable
private fun PreviewMainScreen() {
    MainScreen()
}