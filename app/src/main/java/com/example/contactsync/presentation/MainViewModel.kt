package com.example.contactsync.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsync.domain.usecase.SyncContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SyncStatus {
    object Idle : SyncStatus()
    object Syncing : SyncStatus()
    object Success : SyncStatus()
    data class Error(val message: String) : SyncStatus()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val syncContactsUseCase: SyncContactsUseCase
) : ViewModel() {

    private val _syncStatus = MutableLiveData<SyncStatus>(SyncStatus.Idle)
    val syncStatus: LiveData<SyncStatus> = _syncStatus

    fun syncContacts() {
        viewModelScope.launch {
            _syncStatus.value = SyncStatus.Syncing
            try {
                syncContactsUseCase()
                _syncStatus.value = SyncStatus.Success
            } catch (e: Exception) {
                _syncStatus.value = SyncStatus.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}