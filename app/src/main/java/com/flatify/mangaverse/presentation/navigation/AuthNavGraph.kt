package com.flatify.mangaverse.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.flatify.mangaverse.presentation.ui.MangaScreen.MangaDetailsScreen
import com.flatify.mangaverse.presentation.ui.SignupScreen.AuthViewModel
import com.flatify.mangaverse.presentation.ui.SignupScreen.SignupScreen
import com.flatify.mangaverse.utils.SharedViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
) {
    navigation(startDestination = AuthScreen.SignInScreen.route, route = Graph.AUTHENTICATION){
        composable(route = AuthScreen.SignInScreen.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            SignupScreen(
                viewModel = authViewModel,
                onSignInClick = {
                    navController.navigate(Graph.HOME){
                        popUpTo(Graph.AUTHENTICATION){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}



sealed class AuthScreen(val route : String){
    data object SignInScreen: AuthScreen("sign_in")
}