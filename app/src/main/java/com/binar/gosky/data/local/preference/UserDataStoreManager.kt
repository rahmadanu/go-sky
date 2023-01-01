package com.binar.gosky.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun setUserLogin(isLogin: Boolean) {
        context.userDataStore.edit { preferences ->
            preferences[LOGIN_STATUS_KEY] = isLogin
        }
    }

    fun getUserLoginStatus(): Flow<Boolean> {
        return context.userDataStore.data.map { preferences ->
            preferences[LOGIN_STATUS_KEY] ?: false
        }
    }

    suspend fun setUserAccessToken(accessToken: String) {
        context.userDataStore.edit { preferences ->
            preferences[USER_ACCESS_TOKEN_KEY] = accessToken
        }
    }

    fun getUserAccessToken(): Flow<String> {
        return context.userDataStore.data.map { preferences ->
            preferences[USER_ACCESS_TOKEN_KEY] ?: "kosong"
        }
    }

    suspend fun setUserRole(role: String) {
        context.userDataStore.edit { preferences ->
            preferences[USER_ROLE_KEY] = role
        }
    }

    fun getUserRole(): Flow<String> {
        return context.userDataStore.data.map { preferences ->
            preferences[USER_ROLE_KEY] ?: "null"
        }
    }

    companion object {
        private const val DATA_STORE_NAME = "user_preferences"
        private val LOGIN_STATUS_KEY = booleanPreferencesKey("login_status_key")
        private val USER_ACCESS_TOKEN_KEY = stringPreferencesKey("user_access_token_key")
        private val USER_ROLE_KEY = stringPreferencesKey("user_role_key")

        val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)
    }
}
