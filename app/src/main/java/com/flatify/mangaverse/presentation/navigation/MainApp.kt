package com.flatify.mangaverse.presentation.navigation

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flatify.mangaverse.presentation.ui.BottomNavigationScreen.BottomBar
import com.flatify.mangaverse.presentation.ui.FaceRecognition.FaceRecognitionScreen
import com.flatify.mangaverse.presentation.ui.FaceRecognition.PermissionResultRequest
import com.flatify.mangaverse.presentation.ui.MangaScreen.MangaDetailsScreen
import com.flatify.mangaverse.presentation.ui.MangaScreen.MangaScreen
import com.flatify.mangaverse.presentation.ui.MangaScreen.MangaViewModel
import com.flatify.mangaverse.presentation.ui.SignupScreen.AuthViewModel
import com.flatify.mangaverse.presentation.ui.SignupScreen.SignupScreen
import com.flatify.mangaverse.presentation.ui.theme.BackgroundColor
import com.flatify.mangaverse.presentation.ui.theme.GramatikaTrial
import com.flatify.mangaverse.utils.BottomBarScreen
import com.flatify.mangaverse.utils.MainNavScreen
//import com.flatify.mangaverse.utils.MainNavScreen
import com.flatify.mangaverse.utils.SharedViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainApp(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel(),
    mangaViewModel: MangaViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel(),
    cameraPermissionRequest: PermissionResultRequest,
    context: Context,
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    val showBottomBar = currentRoute in listOf(BottomBarScreen.MangaScreen.route, BottomBarScreen.FaceRecognitionScreen.route)

    val isSignedIn = authViewModel.isSignedIn

    LaunchedEffect(isSignedIn) {
        if(isSignedIn != null){
            Log.d("Auth", "User is signed in: $isSignedIn")
            navController.navigate(BottomBarScreen.MangaScreen.route){
                popUpTo(BottomBarScreen.MangaScreen.route){
                    inclusive = true
                }
            }
        }else{
            navController.navigate(MainNavScreen.SignInScreen.route){
                popUpTo(MainNavScreen.SignInScreen.route){
                    inclusive = true
                }
            }
        }
    }

    val screens = listOf(
        BottomBarScreen.MangaScreen,
        BottomBarScreen.FaceRecognitionScreen
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            if(showBottomBar){
                BottomNavigation(
                    backgroundColor = BackgroundColor,
                    contentColor = Color.Black,
                    modifier = Modifier.height(80.dp),
                ) {
                    screens.forEachIndexed{ index, screen ->
                        BottomNavigationItem(
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route){
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(BottomBarScreen.MangaScreen.route){
                                        saveState = true
                                    }
                                }
                            },
                            icon = {
                                Image(
                                    modifier = Modifier.padding(top = 10.dp).size(45.dp),
                                    painter = painterResource(
                                        id = if (currentRoute == screen.route) {
                                            screen.selectedIcon
                                        } else {
                                            screen.unselectedIcon
                                        }
                                    ), contentDescription = screen.route
                                )
                            },
                        )
                    }
                }
            }
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if(isSignedIn != null) BottomBarScreen.MangaScreen.route else MainNavScreen.SignInScreen.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = MainNavScreen.SignInScreen.route) {
                SignupScreen(
                    viewModel = authViewModel,
                    onSignInClick = {
                        navController.navigate(BottomBarScreen.MangaScreen.route){
                            popUpTo(BottomBarScreen.MangaScreen.route){
                                inclusive = true
                            }
                        }
                        authViewModel.resetState()
                    }
                )
            }

            composable(route = BottomBarScreen.MangaScreen.route){
                MangaScreen(
                    mangaViewModel,
                    sharedViewModel,
                    onClick = {
                        navController.navigate(route = MainNavScreen.MangaDetailsScreen.route)
                    }
                )
            }
            composable(route = BottomBarScreen.FaceRecognitionScreen.route) {
                FaceRecognitionScreen(
                    cameraPermissionRequest = cameraPermissionRequest,
                    context = context,
                )
            }

            composable(route = MainNavScreen.MangaDetailsScreen.route ) {
                MangaDetailsScreen(
                    sharedViewModel = sharedViewModel,
                    backOnCLick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}