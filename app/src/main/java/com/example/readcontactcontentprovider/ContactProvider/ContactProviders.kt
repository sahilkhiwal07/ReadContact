package com.example.readcontactcontentprovider.ContactProvider

import android.content.ContentResolver
import android.net.Uri
import android.provider.ContactsContract
import com.example.readcontactcontentprovider.Models.Contacts

class ContactProviders(private val contentResolver: ContentResolver) {

    private lateinit var contacts: Contacts
    private var totalContacts = 0

    companion object {
        var data = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone._ID
        )

        private var CONTENT_URL: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        private var ORDER = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
    }

    suspend fun getAllContactsFromProvider(): List<Contacts> {

        var listContact: MutableList<Contacts> = ArrayList()

        var rs: ContentResolver = contentResolver

        rs.query(CONTENT_URL, data, null, null, ORDER)?.let { cursor ->

            totalContacts = cursor.count

            if (cursor.moveToNext()) {

                for (i in 0 until totalContacts) {
                    contacts = Contacts(
                        id = cursor.getString(cursor.getColumnIndexOrThrow("_id")),
                        name = cursor.getString(cursor.getColumnIndexOrThrow("display_name")),
                        num = cursor.getString(cursor.getColumnIndexOrThrow("data1"))
                    )

                    listContact.add(contacts)
                    cursor.moveToNext()
                }

                cursor.close()
            }
        }

        return listContact

    }


}