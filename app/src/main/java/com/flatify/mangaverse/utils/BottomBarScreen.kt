package com.flatify.mangaverse.utils

import com.flatify.mangaverse.R

sealed class BottomBarScreen (
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val textLabel: String
) {
    data object MangaScreen : BottomBarScreen(
        route = "manga",
        selectedIcon = R.drawable.comic__1_,
        unselectedIcon = R.drawable.comic,
        textLabel = "Manga"
    )

    data object FaceRecognitionScreen : BottomBarScreen(
        route = "face_recognition",
        selectedIcon = R.drawable.face_id__1_,
        unselectedIcon = R.drawable.face_recog,
        textLabel = "Face Recognition"
    )
}