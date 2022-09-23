package com.example.portfolio.repository.firebasemodule

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import com.example.portfolio.R
import com.example.portfolio.RestaurantMenu
import com.example.portfolio.Review
import com.example.portfolio.User
import com.example.portfolio.ui.screen.util.number1Digits
import com.example.portfolio.ui.screen.util.number2Digits
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

object FirebaseObject {
    private val fireStorageInstance = Firebase.storage

    fun getDefaultUrl(callback: (Uri) -> Unit) {
        fireStorageInstance.reference
            .child("delivery_app/roadingimage.jpg")
            .downloadUrl.addOnSuccessListener {
                callback(it)
            }
    }

    fun dbSettingInit(context: Context) {
        val fireStoreInstance = Firebase.firestore

        val testImage = listOf(
            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_01}"),
            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_02}"),
            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_03}"),
            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_04}"),
            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_05}"),
        )

        val testUserImage = listOf(
            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_user_01}"),
            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_user_02}"),
            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_user_03}"),
        )

        Log.d("firebaseTest", "packageName :: ${context.packageName}")

        val imageFilepath = mutableListOf<File>()
//        testImage.forEach {
//            Log.d("firebaseTest", "testImage path :: ${it.path}")
//            val tt = File(it.path!!)
//            Log.d("firebaseTest", "testImage Filepath :: ${tt.path}, name:: ${tt.name}")
//
//            imageFilepath.add(File(it.path!!))
//        }

        val userFilePath = mutableListOf<File>()
//        testUserImage.forEach {
//            Log.d("firebaseTest", "testUser path :: ${it.path}")
//            val tt = File(it.path!!)
//            Log.d("firebaseTest", "testUser Filepath :: ${tt.path}, name:: ${tt.name}")
//            userFilePath.add(File(it.path!!))
//        }

        val uploadResultPath = mutableListOf<Uri>()
//        testUserImage.forEach {
////            val useUri = Uri.fromFile(it)
//            val ref = fireStorageInstance.reference.child("delivery_app/test/${it.lastPathSegment}.jpg")
//
//            ref.putFile(it)
//                .continueWithTask { task ->
//                    if (!task.isSuccessful) {
//                        task.exception?.let { e->
//                            throw e
//                        }
//                    }
//                    ref.downloadUrl
//                }
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val userId = UUID.randomUUID().toString()
//
//                        fireStoreInstance.collection("delivery").document("users")
//                            .collection("user").document(userId)
//                            .set(User(
//                                profileImage = task.result,
//                                name = "test",
//                                phoneNumber = "010-1100-1100",
//                                id = userId
//                            ))
//                    }
//                }
//        }

        val restaurantId = "testId"

        fireStoreInstance
            .collection("delivery")
            .document("users")
            .collection("user")
            .get()
            .addOnSuccessListener { result ->

                val dayCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate.now().toString()
                } else {
                    val simple = SimpleDateFormat("yyyy-M-dd", Locale.KOREA)
                    val current = simple.format(Date())
                    current
                }
                result.forEachIndexed { index, userData ->
                    fireStoreInstance.collection("delivery")
                        .document("reviews")
                        .collection("review")
                        .document("test")
                        .collection(restaurantId)
                        .document("review_$index")
                        .set(
                            Review(
                                takePicture = null,
                                rating = Random.nextDouble(2.3, 5.0).toFloat().number1Digits(),
                                content = "테스트용 평가 내용입니다.",
                                date = dayCheck,
                                userId = userData.id,
                                restaurantId = restaurantId
                            )
                        )
                }
            }

        testImage.forEachIndexed { index, uri ->

            fireStorageInstance.reference
                .child("delivery_app/test/menu/${uri.lastPathSegment}.jpg")
                .apply {
                    putFile(uri)
                        .continueWithTask { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let { e->
                                    throw e
                                }
                            }
                            downloadUrl
                        }
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                fireStoreInstance.collection("delivery")
                                    .document("restaurantMenu")
                                    .collection("test")
                                    .document(restaurantId)
                                    .collection("menu")
                                    .document("menu_$index")
                                    .set(
                                        RestaurantMenu(
                                            restaurantId = restaurantId,
                                            image = task.result,
                                            menuName = "테스트 메뉴입니다.",
                                            price = "12100 원",
                                            detailContent = "메뉴를 위한 간단한 설명을 해 주세요."
                                        )
                                    )
                            }
                        }
                }
        }
    }
}