package com.example.readcontactcontentprovider

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readcontactcontentprovider.Adapters.ContactAdapter
import com.example.readcontactcontentprovider.ContactProvider.ContactProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val REQUEST_PERMISSION_FOR_READ_CONTACT = 1

class MainActivity : AppCompatActivity() {

    var contactProviders: ContactProviders? = null
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactProviders = ContactProviders(contentResolver)

        setRecyclerView()
        checkForContactPermission()

    }

    private fun getPhoneData() {
        GlobalScope.launch(Dispatchers.Main) {
            val operate = withContext(Dispatchers.IO) { contactProviders?.getAllContactsFromProvider() }
            if (operate != null) {
                adapter.submitList(operate)
            }

        }
    }

    private fun checkForContactPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, Array(1) { Manifest.permission.READ_CONTACTS },
                REQUEST_PERMISSION_FOR_READ_CONTACT
            )
        } else {
            getPhoneData()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_PERMISSION_FOR_READ_CONTACT -> {
                if (permissions[0].contentEquals(Manifest.permission.READ_CONTACTS) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhoneData()
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Fail to Obtained Permission", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }


    private fun setRecyclerView() {
        adapter = ContactAdapter(this)
        recyclerVIew.adapter = adapter
        recyclerVIew.layoutManager = LinearLayoutManager(this)
    }


}
