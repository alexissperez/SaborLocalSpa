package com.example.saborlocalspa.viewmodel

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.saborlocalspa.repository.UserRepository
import androidx.lifecycle.ViewModel
import com.example.saborlocalspa.utils.ImageStorageUtil
import com.example.saborlocalspa.repository.AvatarRepository
import android.content.Context
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope


data class ProfileUiState(
    val userName: String = "",
    val avatarUrl: String? = null,
    val isLoading: Boolean = false,
    val userEmail: String = "",
    val error: String? = null,
    val formattedCreatedAt: String = "",
    val avatarUri: Uri? = null
)

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val avatarRepository: AvatarRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun loadAvatar(context: Context) {
        val uri = ImageStorageUtil.loadAvatarUri(context)
        updateAvatar(uri)
    }

    fun saveAvatarUri(uri: Uri) {
        viewModelScope.launch {
            avatarRepository.saveAvatarUri(uri)
            _uiState.update { it.copy(avatarUri = uri) }
        }
    }

    fun observeAvatarUri() {
        viewModelScope.launch {
            avatarRepository.getAvatarUri().collect { uri ->
                _uiState.update { it.copy(avatarUri = uri) }
            }
        }
    }

    fun updateAvatar(uri: Uri?) {
        _uiState.update { it.copy(avatarUri = uri) }
    }

    fun updateUserName(nuevoNombre: String) {
        _uiState.update { it.copy(userName = nuevoNombre) }
    }

    fun updateUserEmail(nuevoEmail: String) {
        _uiState.update { it.copy(userEmail = nuevoEmail) }
    }
}

