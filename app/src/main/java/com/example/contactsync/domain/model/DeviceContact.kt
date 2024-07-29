package com.example.contactsync.domain.model

import java.net.URI

data class DeviceContact(
    val name: String,
    val phoneNumber: String,
    val profilePicture: URI?,
    val dob: String?
)
