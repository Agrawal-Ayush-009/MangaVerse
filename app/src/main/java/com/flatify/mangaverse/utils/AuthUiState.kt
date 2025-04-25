package com.flatify.mangaverse.utils

sealed class RoomUiState {
    object Idle : RoomUiState()
    object Loading : RoomUiState()
    object Success : RoomUiState()
    data class Error(val message: String) : RoomUiState()
}