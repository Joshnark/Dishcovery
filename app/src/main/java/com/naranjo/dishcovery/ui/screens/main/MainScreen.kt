package com.naranjo.dishcovery.ui.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naranjo.dishcovery.R
import com.naranjo.dishcovery.ui.screens.main.models.PageData
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeScreen
import com.naranjo.dishcovery.ui.theme.NANO
import com.naranjo.dishcovery.ui.theme.TINY
import com.naranjo.dishcovery.ui.views.SmallSpacer
import kotlinx.coroutines.launch

private const val ICON_SIZE = 20

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val pageData = listOf(
        PageData(
            outlineIcon = R.drawable.ic_search,
            selectedIcon = R.drawable.ic_search_selected,
            screen = { Text("1") }
        ),
        PageData(
            outlineIcon = R.drawable.ic_home,
            selectedIcon = R.drawable.ic_home_selected,
            screen = { HomeScreen() }
        ),
        PageData(
            outlineIcon = R.drawable.ic_like,
            selectedIcon = R.drawable.ic_like_selected,
            screen = { Text("3") }
        )
    )

    val pagerState = rememberPagerState(
        pageCount = {
            pageData.size
        },
        initialPage = pageData.size / 2
    )

    Scaffold(
        bottomBar = {
            TabBar(
                pagerState = pagerState,
                pageData = pageData
            )
        },
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(padding),
            userScrollEnabled = false
        ) { pageIndex ->
            pageData[pageIndex].screen()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabBar(pagerState: PagerState, pageData: List<PageData>) {
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
        pageData.forEachIndexed { index, value ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
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

@Preview
@Composable
private fun PreviewMainScreen() {
    MainScreen()
}