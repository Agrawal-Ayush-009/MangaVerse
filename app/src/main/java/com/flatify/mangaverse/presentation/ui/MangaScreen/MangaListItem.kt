package com.flatify.mangaverse.presentation.ui.MangaScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.flatify.android.PrimaryButton
import com.flatify.mangaverse.R
import com.flatify.mangaverse.presentation.ui.theme.BackgroundColor
import com.flatify.mangaverse.presentation.ui.theme.GramatikaTrial
import com.flatify.mangaverse.presentation.ui.theme.PlaceholderColor

@Composable
fun MangaListItem(
    title: String,
    status: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .width(120.dp)
                .height(180.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    imageUrl
                )
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.default_placeholder),
            placeholder = painterResource(id = R.drawable.default_placeholder),
            contentDescription = "manga_poster",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Manga",
                fontSize = 14.sp,
                color = PlaceholderColor,
                fontWeight = FontWeight.Bold,
                fontFamily = GramatikaTrial
            )
            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = GramatikaTrial,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Status",
                fontSize = 14.sp,
                color = PlaceholderColor,
                fontWeight = FontWeight.Bold,
                fontFamily = GramatikaTrial
            )
            Text(
                text = status,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = GramatikaTrial
            )

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = "See Details"
            ) {
                onClick()
            }
        }
    }
}