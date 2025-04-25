package com.flatify.mangaverse.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatify.mangaverse.presentation.ui.BottomNavigationScreen.BottomNavigationScreen
import com.flatify.mangaverse.presentation.ui.FaceRecognition.PermissionResultRequest
import com.flatify.mangaverse.presentation.ui.MangaScreen.MangaViewModel
import com.flatify.mangaverse.presentation.ui.SignupScreen.AuthViewModel
import com.flatify.mangaverse.presentation.ui.SignupScreen.SignupScreen
import com.flatify.mangaverse.utils.SharedViewModel

@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController(),
    context: Context,
    cameraPermissionRequest: PermissionResultRequest
) {
//    val isSignedIn = authViewModel.isSignedIn

//    LaunchedEffect(isSignedIn) {
//        if(isSignedIn != null){
//            navController.navigate(Graph.HOME){
//                popUpTo(Graph.AUTHENTICATION){
//                    inclusive = true
//                }
//            }
//        }else{
//            navController.navigate(Graph.AUTHENTICATION){
//                popUpTo(Graph.AUTHENTICATION){
//                    inclusive = true
//                }
//            }
//        }
//    }

    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = Graph.AUTHENTICATION
    ){
        authNavGraph(navController)
        composable(Graph.HOME) {
            BottomNavigationScreen(
                navController = navController,
                context = context,
                cameraPermissionRequest = cameraPermissionRequest
            )
        }
    }
}

object Graph {
    const val MAIN = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}