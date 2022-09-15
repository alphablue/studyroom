package com.example.portfolio.repository.firebasemodule

import android.net.Uri
import androidx.compose.runtime.Composable
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

object FirebaseObject {
    val fireStoreInstance = Firebase.storage

    fun getDefaultUrl(callback: (Uri) -> Unit) {
        fireStoreInstance.reference
            .child("delivery_app/roadingimage.jpg")
            .downloadUrl.addOnSuccessListener {
                callback(it)
            }
    }
}