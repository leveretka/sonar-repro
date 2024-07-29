package com.example.contactsync.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey val phoneNumber: String,
    val name: String,
    val profilePicture: String?, // Store URI as String
    val dob: String?
)