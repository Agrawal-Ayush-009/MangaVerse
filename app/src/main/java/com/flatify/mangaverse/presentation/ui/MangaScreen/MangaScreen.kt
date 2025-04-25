package com.flatify.mangaverse.presentation.ui.MangaScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.flatify.mangaverse.R
import com.flatify.mangaverse.data.remote.model.toDomain
import com.flatify.mangaverse.presentation.ui.theme.BackgroundColor
import com.flatify.mangaverse.presentation.ui.theme.ButtonColor2
import com.flatify.mangaverse.presentation.ui.theme.ErrorRed
import com.flatify.mangaverse.presentation.ui.theme.GramatikaTrial
import com.flatify.mangaverse.utils.ObserveNetworkState
import com.flatify.mangaverse.utils.RoomUiState
import com.flatify.mangaverse.utils.SharedViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MangaScreen(
    viewModel: MangaViewModel,
    sharedViewModel: SharedViewModel,
    onClick: () -> Unit
) {
    val mangaList = viewModel.mangaList.collectAsLazyPagingItems()


    val isRefreshing = mangaList.loadState.refresh is LoadState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { mangaList.refresh() }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)

    ) {
        Column {
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 50.dp),
                text = "Mangas",
                fontSize = 32.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = GramatikaTrial
            )
            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.pullRefresh(pullRefreshState)
            ) {
                items(mangaList.itemCount) { index ->
                    mangaList[index]?.let {
                        MangaListItem(
                            title = it.title,
                            status = it.status,
                            imageUrl = it.thumb,
                            onClick = {
                                sharedViewModel.setSelectedManga(it.toDomain())
                                onClick()
                            }
                        )
                    }
                }

                mangaList.apply {
                    when (loadState.refresh) {
                        is LoadState.Loading -> item {
                        }

                        is LoadState.Error -> item {
                            Log.d("manga", "error: ${loadState.refresh as LoadState.Error}")
                            Row(
                                modifier = Modifier
                                    .padding(20.dp, 10.dp, 20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start

                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .size(12.dp),
                                    painter = painterResource(id = R.drawable.lucide_info),
                                    contentDescription = "trophy",
                                    alignment = Alignment.Center,
                                    colorFilter = ColorFilter.tint(color = ErrorRed)
                                )

                                Text(
                                    text = "Error: ${(loadState.refresh as LoadState.Error).error.localizedMessage}",
                                    fontSize = 12.sp,
                                    fontFamily = GramatikaTrial,
                                    fontWeight = FontWeight.Normal,
                                    color = ErrorRed
                                )
                            }
                        }

                        else -> {}
                    }
                    when (loadState.append) {
                        is LoadState.Loading -> item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp, 10.dp, 20.dp, 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(32.dp),
                                    color = ButtonColor2
                                )
                            }
                        }

                        is LoadState.Error -> item {
                            Row(
                                modifier = Modifier
                                    .padding(20.dp, 10.dp, 20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start

                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .size(12.dp),
                                    painter = painterResource(id = R.drawable.lucide_info),
                                    contentDescription = "trophy",
                                    alignment = Alignment.Center,
                                    colorFilter = ColorFilter.tint(color = ErrorRed)
                                )

                                Text(
                                    text = "Paging Error",
                                    fontSize = 12.sp,
                                    fontFamily = GramatikaTrial,
                                    fontWeight = FontWeight.Normal,
                                    color = ErrorRed
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = ButtonColor2
        )

    }
}