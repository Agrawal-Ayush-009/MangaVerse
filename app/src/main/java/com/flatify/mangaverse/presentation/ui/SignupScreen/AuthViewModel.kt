package com.flatify.mangaverse.presentation.ui.SignupScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatify.mangaverse.data.local.dao.UserDao
import com.flatify.mangaverse.data.local.entity.UserEntity
import com.flatify.mangaverse.data.prefs.UserPreferences
import com.flatify.mangaverse.utils.NetworkResult
import com.flatify.mangaverse.utils.RoomUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userDao: UserDao,
    private val prefs: UserPreferences
): ViewModel() {
    var isSignedIn by mutableStateOf<String?>(null)

    private val _authUiState = MutableStateFlow<RoomUiState>(RoomUiState.Idle)
    val authUiState: StateFlow<RoomUiState> = _authUiState.asStateFlow()

    init {
        viewModelScope.launch {
            prefs.signedInEmail.collect { email ->
                if(email != null){
                    Log.d("AuthViewModel", "User is signed in: $email")
                }
                isSignedIn = email
            }
        }
    }

    fun singIn(email: String, password: String){
        viewModelScope.launch {
            _authUiState.value = RoomUiState.Loading
            try{
                val user = userDao.getUserByEmail(email)
                if (user == null) {
                    val newUser = UserEntity(email = email, password = password)
                    userDao.insertUser(newUser)
                    prefs.signIn(email)
                    _authUiState.value = RoomUiState.Success
                } else if (user.password == password) {
                    prefs.signIn(email)
                    _authUiState.value = RoomUiState.Success
                }
            }catch (e: Exception){
                _authUiState.value = RoomUiState.Error("Something went wrong: ${e.message}")
            }
            val user = userDao.getUserByEmail(email)
            if (user == null) {
                val newUser = UserEntity(email = email, password = password)
                userDao.insertUser(newUser)
                prefs.signIn(email)
            } else if (user.password == password) {
                prefs.signIn(email)
            }
        }
    }

    fun resetState() {
        _authUiState.value = RoomUiState.Idle
    }
}