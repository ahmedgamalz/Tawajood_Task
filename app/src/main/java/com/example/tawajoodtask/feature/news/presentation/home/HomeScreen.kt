@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.tawajoodtask.feature.news.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tawajoodtask.feature.news.domain.model.Article
import com.example.tawajoodtask.feature.news.domain.model.Category
import com.example.tawajoodtask.feature.news.presentation.composable.ArticleItem
import com.example.tawajoodtask.feature.news.presentation.composable.CustomPullToRefreshIndicator
import com.example.tawajoodtask.feature.news.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    
    HomeScreenContent(
        state = state,
        onCategoryClick = { viewModel.loadNews(it) },
        onArticleClick = { article ->
            navController.navigate(Screen.Details.createRoute(article.url))
        }
    )
}

@Composable
fun HomeScreenContent(
    state: HomeUiState,
    onCategoryClick: (String) -> Unit,
    onArticleClick: (Article) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { Category.entries.size })
    val selectedCategoryIndex = Category.entries.indexOfFirst { it.value == state.selectedCategory }

    LaunchedEffect(state.selectedCategory) {
        if (selectedCategoryIndex != -1 && pagerState.currentPage != selectedCategoryIndex) {
            pagerState.animateScrollToPage(selectedCategoryIndex)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val category = Category.entries[pagerState.currentPage].value
        if (state.selectedCategory != category) {
            onCategoryClick(category)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Feed", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(Category.entries) { category ->
                    FilterChip(
                        selected = state.selectedCategory == category.value,
                        onClick = {
                            val index = Category.entries.indexOf(category)
                            if (index != -1) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        },
                        label = {
                            Text(
                                text = category.value.replaceFirstChar { it.uppercase() },
                                fontWeight = if (state.selectedCategory == category.value) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { pageIndex ->
                val isCurrentPage = pageIndex == selectedCategoryIndex
                val articles = if (isCurrentPage) state.articles else emptyList()
                val isRefreshing = state.isLoading && isCurrentPage
                val pullToRefreshState = rememberPullToRefreshState()

                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = { onCategoryClick(Category.entries[pageIndex].value) },
                    state = pullToRefreshState,
                    indicator = {
                        CustomPullToRefreshIndicator(
                            isRefreshing = isRefreshing,
                            state = pullToRefreshState,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(articles) { article ->
                            ArticleItem(
                                article = article,
                                onClick = { onArticleClick(article) }
                            )
                        }
                    }
                }
            }
        }
    }
}