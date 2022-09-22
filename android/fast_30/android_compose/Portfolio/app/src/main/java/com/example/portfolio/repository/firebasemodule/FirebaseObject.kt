package com.example.portfolio.repository.firebasemodule

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

object FirebaseObject {
    private val fireStorageInstance = Firebase.storage

    fun getDefaultUrl(callback: (Uri) -> Unit) {
        fireStorageInstance.reference
            .child("delivery_app/roadingimage.jpg")
            .downloadUrl.addOnSuccessListener {
                callback(it)
            }
    }

    fun dbSettingInit() {
    }
}