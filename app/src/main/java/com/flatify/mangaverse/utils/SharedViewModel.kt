package com.flatify.mangaverse.utils

import android.util.Log
import androidx.lifecycle.ViewModel
import com.flatify.mangaverse.data.remote.model.MangaData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(): ViewModel() {
    private var _selectedManga : MangaData? = null
    val selectedManga : MangaData? get() = _selectedManga

    fun setSelectedManga(manga: MangaData){
        _selectedManga = manga
        Log.d("manga", selectedManga.toString())
    }
}