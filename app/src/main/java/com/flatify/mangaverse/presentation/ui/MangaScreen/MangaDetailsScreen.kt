package com.flatify.mangaverse.presentation.ui.MangaScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.flatify.mangaverse.R
import com.flatify.mangaverse.data.remote.model.MangaData
import com.flatify.mangaverse.presentation.ui.theme.GramatikaTrial
import com.flatify.mangaverse.utils.SharedViewModel

@Composable
fun MangaDetailsScreen(
    sharedViewModel: SharedViewModel,
    backOnCLick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        sharedViewModel.selectedManga?.let { manga->
            Log.d("manga1", manga.toString())
            Box(
                modifier = Modifier
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .height(200.dp)
                ) {

                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                manga.thumb
                            )
                            .crossfade(true)
                            .build(),
                        error = painterResource(id = R.drawable.default_placeholder),
                        placeholder = painterResource(id = R.drawable.default_placeholder),
                        contentDescription = "manga_poster",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .height(200.dp)
                    )
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    backOnCLick()
                                },
                            painter = painterResource(R.drawable.arrow_ios_back_svgrepo_com),
                            contentDescription = "back_arrow",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        Text(
                            text = "Manga Details",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontFamily = GramatikaTrial,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .width(150.dp)
                            .height(225.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                manga.thumb
                            )
                            .crossfade(true)
                            .build(),
                        error = painterResource(id = R.drawable.default_placeholder),
                        placeholder = painterResource(id = R.drawable.default_placeholder),
                        contentDescription = "manga_poster",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Title",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = GramatikaTrial,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = manga.title,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontFamily = GramatikaTrial,
                    fontWeight = FontWeight.SemiBold,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Genre",
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontFamily = GramatikaTrial,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            modifier = Modifier.padding(top = 5.dp),
                            text = manga.genres[0],
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontFamily = GramatikaTrial,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Type",
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontFamily = GramatikaTrial,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            modifier = Modifier.padding(top = 5.dp),
                            text = manga.type,
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontFamily = GramatikaTrial,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "Summary",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = GramatikaTrial,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = manga.summary,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontFamily = GramatikaTrial,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}