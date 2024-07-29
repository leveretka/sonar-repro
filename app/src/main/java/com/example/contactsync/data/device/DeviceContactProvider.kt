package com.example.contactsync.data.device

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import com.example.contactsync.domain.model.DeviceContact
import java.net.URI
import javax.inject.Inject

class DeviceContactProvider @Inject constructor(
    private val contentResolver: ContentResolver
) {
    enum class OrderBy {
        NAME, PHONE_NUMBER
    }

    fun getDeviceContacts(orderBy: OrderBy, count: Int, offset: Int): List<DeviceContact> {
        val contacts = mutableListOf<DeviceContact>()
        val sortOrder = when (orderBy) {
            OrderBy.NAME -> "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
            OrderBy.PHONE_NUMBER -> "${ContactsContract.CommonDataKinds.Phone.NUMBER} ASC"
        }

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.CommonDataKinds.Event.START_DATE
        )

        val selection = "${ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER} = ?"
        val selectionArgs = arrayOf("1")

        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            if (cursor.moveToPosition(offset)) {
                var contactsRetrieved = 0
                do {
                    val contact = cursor.toDeviceContact()
                    contacts.add(contact)
                    contactsRetrieved++
                } while (cursor.moveToNext() && contactsRetrieved < count)
            }
        }

        return contacts
    }

    private fun Cursor.toDeviceContact(): DeviceContact {
        val nameIndex = getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val numberIndex = getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val photoUriIndex = getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
        val dobIndex = getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE)

        val name = getString(nameIndex) ?: ""
        val phoneNumber = getString(numberIndex) ?: ""
        val photoUri = getString(photoUriIndex)?.let { URI(it) }
        val dob = getString(dobIndex)

        return DeviceContact(name, phoneNumber, photoUri, dob)
    }
}