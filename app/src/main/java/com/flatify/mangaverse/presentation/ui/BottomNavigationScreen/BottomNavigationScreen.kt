package com.flatify.mangaverse.presentation.ui.BottomNavigationScreen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.flatify.mangaverse.presentation.navigation.BottomNavGraph
import com.flatify.mangaverse.presentation.ui.FaceRecognition.FaceRecognitionScreen
import com.flatify.mangaverse.presentation.ui.FaceRecognition.PermissionResultRequest
import com.flatify.mangaverse.presentation.ui.MangaScreen.MangaScreen
import com.flatify.mangaverse.presentation.ui.MangaScreen.MangaViewModel
import com.flatify.mangaverse.utils.BottomBarScreen
import com.flatify.mangaverse.presentation.ui.theme.BackgroundColor
import com.flatify.mangaverse.presentation.ui.theme.GramatikaTrial
import com.flatify.mangaverse.utils.SharedViewModel

@Composable
fun BottomNavigationScreen(
    navController: NavHostController,
    context: Context,
    cameraPermissionRequest: PermissionResultRequest
) {
    Scaffold(
        bottomBar = { BottomBar(navController) },
        modifier = Modifier.background(BackgroundColor),
        backgroundColor = BackgroundColor
    ) { paddingValues ->
        BottomNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            context = context,
            cameraPermissionRequest = cameraPermissionRequest
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.MangaScreen,
        BottomBarScreen.FaceRecognitionScreen
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    BottomNavigation(
        backgroundColor = BackgroundColor,
        contentColor = Color.Black,
        modifier = Modifier.height(80.dp),
    ) {
        screens.forEachIndexed{ index, screen ->
            BottomNavigationItem(
                selected = index == selectedItemIndex,
                onClick = {
                    selectedItemIndex = index
                },
                icon = {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(
                            id = if (index == selectedItemIndex) {
                                screen.selectedIcon
                            } else {
                                screen.unselectedIcon
                            }
                        ), contentDescription = screen.route
                    )
                },
                label = {
                    Text(
                        text =  screen.textLabel,
                        fontSize = 8.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontFamily = GramatikaTrial
                    )
                }
            )
        }
    }
}