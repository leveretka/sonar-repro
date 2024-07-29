package com.example.contactsync.data.remote

import com.example.contactsync.domain.model.DeviceContact
import kotlinx.coroutines.delay
import javax.inject.Inject

class ContactsNetworkBridge @Inject constructor() {

    suspend fun syncContacts(changes: List<DeviceContact>, deleted: List<String>): Boolean {
        // Stub implementation
        delay(1000) // Simulate network delay
        return true
    }
}