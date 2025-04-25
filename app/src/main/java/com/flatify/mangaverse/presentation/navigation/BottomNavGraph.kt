package com.flatify.mangaverse.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.flatify.mangaverse.presentation.ui.FaceRecognition.FaceRecognitionScreen
import com.flatify.mangaverse.presentation.ui.FaceRecognition.PermissionResultRequest
import com.flatify.mangaverse.presentation.ui.MangaScreen.MangaDetailsScreen
import com.flatify.mangaverse.presentation.ui.MangaScreen.MangaScreen
import com.flatify.mangaverse.presentation.ui.MangaScreen.MangaViewModel
import com.flatify.mangaverse.presentation.ui.SignupScreen.AuthViewModel
import com.flatify.mangaverse.utils.BottomBarScreen
import com.flatify.mangaverse.utils.SharedViewModel

@Composable
fun BottomNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    context: Context,
    cameraPermissionRequest: PermissionResultRequest
) {
    val mangaViewModel: MangaViewModel = hiltViewModel()
    val sharedViewModel: SharedViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.MangaScreen.route,
        route = Graph.HOME
    ) {
        composable(route = BottomBarScreen.MangaScreen.route) {
            MangaScreen(
                viewModel = mangaViewModel,
                sharedViewModel = sharedViewModel,
                onClick = {
                    navController.navigate(route = DetailsScreen.MangaDetailsScreen.route)
                }
            )
        }

        composable(route = BottomBarScreen.FaceRecognitionScreen.route) {
            FaceRecognitionScreen(
                context = context,
                cameraPermissionRequest = cameraPermissionRequest
            )
        }

        DetailsNavGraph(navController = navController, sharedViewModel = sharedViewModel)
    }
}

fun NavGraphBuilder.DetailsNavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.MangaDetailsScreen.route
    ) {
        composable(route = DetailsScreen.MangaDetailsScreen.route) {
            MangaDetailsScreen(
                sharedViewModel = sharedViewModel,
                backOnCLick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object MangaDetailsScreen : DetailsScreen(route = "manga_details")
}