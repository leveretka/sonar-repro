package com.example.contactsync.domain.usecase

import com.example.contactsync.data.local.ContactEntity
import com.example.contactsync.data.remote.ContactsNetworkBridge
import com.example.contactsync.domain.model.DeviceContact
import javax.inject.Inject

class SyncContactsUseCase @Inject constructor(private val contactsNetworkBridge: ContactsNetworkBridge) {
    suspend operator fun invoke() {
        val data = listOf<DeviceContact>()

        data.chunked(5) {
//            contactsNetworkBridge.syncContacts(it, emptyList())
        }

    }
}
