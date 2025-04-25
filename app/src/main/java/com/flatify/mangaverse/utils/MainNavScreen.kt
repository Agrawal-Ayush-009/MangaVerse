package com.flatify.mangaverse.utils

sealed class MainNavScreen(
     val route: String
) {
    object SignInScreen : MainNavScreen(route = "sign_in_screen")
    object MangaDetailsScreen : MainNavScreen(route = "manga_details_screen")
}