package com.flatify.mangaverse.presentation.ui.MangaScreen

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.flatify.mangaverse.data.local.entity.MangaEntity
import com.flatify.mangaverse.data.remote.model.MangaData
import com.flatify.mangaverse.domain.repository.MangaRepositoryImp
import com.flatify.mangaverse.utils.RoomUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val repository: MangaRepositoryImp,
    private val app: Application
) : ViewModel() {

    val mangaList = repository.getManga().cachedIn(viewModelScope)

    private val _mangaUiState = MutableStateFlow<RoomUiState>(RoomUiState.Idle)
    val mangaUiState: StateFlow<RoomUiState> = _mangaUiState.asStateFlow()
    var mangaRoomList = listOf<MangaData>()

    fun cacheManga(){
        viewModelScope.launch {
            repository.cacheManga()
        }
    }

    fun getCachedManga() {
        viewModelScope.launch {
            _mangaUiState.value = RoomUiState.Loading
            try {
                val response = repository.getCachedManga()
                mangaRoomList = response
                _mangaUiState.value = RoomUiState.Success
            }catch (e: Exception){
                _mangaUiState.value = RoomUiState.Error("Something went wrong: ${e.message}")
            }
        }
    }

//    fun isOnline(context: Context): Boolean {
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return cm.activeNetworkInfo?.isConnected == true
//    }
//
//    val mangaList: Flow<PagingData<MangaData>> = if(isOnline(app)){
//        repository.getManga().onEach{
//            data ->
//            repository.getManga().cachedIn(viewModelScope)
//        }
//
//    }else{
//        flow {
//            val cached = repository.getCachedManga()
//            emit(PagingData.from(cached))
//        }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
//    }
}
