package com.example.contactsync.data.remote

import com.example.contactsync.domain.model.DeviceContact
import kotlinx.coroutines.delay
import javax.inject.Inject

class ContactsNetworkBridge @Inject constructor() {

    /**
     * @param changes: List of Device contacts that have been updated
     * @param deleted_phonenumbers: List of phone numbers that have been deleted
     */
    suspend fun syncContacts(
        changes: List<DeviceContact>, deleted_phonenumbers: List<String>
    ): Boolean {
        // Stub implementation
        delay(1000) // Simulate network delay
        return true
    }
}