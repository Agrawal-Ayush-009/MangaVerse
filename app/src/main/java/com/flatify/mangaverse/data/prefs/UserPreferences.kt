package com.flatify.mangaverse.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")
    private val emailKey = stringPreferencesKey("signed_in_email")
    private val dataStore = context.dataStore

    val signedInEmail: Flow<String?> = dataStore.data.map { it[emailKey] }

    suspend fun signIn(email: String) {
        dataStore.edit { it[emailKey] = email }
    }

    suspend fun signOut() {
        dataStore.edit { it.remove(emailKey) }
    }
}