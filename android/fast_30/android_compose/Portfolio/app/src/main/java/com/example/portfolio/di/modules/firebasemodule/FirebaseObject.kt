package com.example.portfolio.di.modules.firebasemodule

import android.net.Uri
import android.util.Log
import com.example.portfolio.model.uidatamodels.RestaurantMenu
import com.example.portfolio.model.uidatamodels.Review
import com.example.portfolio.model.uidatamodels.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
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

    fun getTestMenus(callback: (List<RestaurantMenu>) -> Unit) {
        val fireStoreInstance = Firebase.firestore

        fireStoreInstance
            .collection("delivery")
            .document("restaurantMenu")
            .collection("test")
            .document("testId")
            .collection("menu")
            .get()
            .addOnSuccessListener { results ->
                callback(results.toObjects<RestaurantMenu>())
            }
    }

    fun getTestReview(callback: (List<Review>) -> Unit) {
        val fireStoreInstance = Firebase.firestore

        fireStoreInstance
            .collection("delivery")
            .document("reviews")
            .collection("review")
            .document("test")
            .collection("testId")
            .get()
            .addOnSuccessListener {
                callback(it.toObjects())
            }
    }

    fun addUserId(
        uid: String,
        user: User
    ) {
        val fireStoreInstance = Firebase.firestore

        fireStoreInstance
            .collection("delivery")
            .document("users")
            .collection("user")
            .document(uid)
            .set(
                user
            )
            .addOnCompleteListener {
                Log.d("testSignIn", "SignUp complete")
            }
    }

    fun deleteUserId(
        uid: String
    ){
        val fireStoreInstance = Firebase.firestore

        fireStoreInstance
            .collection("delivery")
            .document("users")
            .collection("user")
            .document(uid)
            .delete()
    }

    fun getUser(
        uid: String,
        callback: (User?) -> Unit
    ) {
        val fireStoreInstance = Firebase.firestore

        fireStoreInstance
            .collection("delivery")
            .document("users")
            .collection("user")
            .document(uid)
            .get()
            .addOnSuccessListener {
                callback(it.toObject<User>())
            }
    }

//    fun dbSettingInit(context: Context) {
//        val fireStoreInstance = Firebase.firestore
//
//        val testImage = listOf(
//            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_01}"),
//            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_02}"),
//            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_03}"),
//            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_04}"),
//            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_05}"),
//        )
//
//        val testUserImage = listOf(
//            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_user_01}"),
//            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_user_02}"),
//            Uri.parse("android.resource://${context.packageName}/${R.drawable.test_user_03}"),
//        )
//
//        Log.d("firebaseTest", "packageName :: ${context.packageName}")
//
//        val imageFilepath = mutableListOf<File>()
//        testImage.forEach {
//            Log.d("firebaseTest", "testImage path :: ${it.path}")
//            val tt = File(it.path!!)
//            Log.d("firebaseTest", "testImage Filepath :: ${tt.path}, name:: ${tt.name}")
//
//            imageFilepath.add(File(it.path!!))
//        }
//
//        val userFilePath = mutableListOf<File>()
//        testUserImage.forEach {
//            Log.d("firebaseTest", "testUser path :: ${it.path}")
//            val tt = File(it.path!!)
//            Log.d("firebaseTest", "testUser Filepath :: ${tt.path}, name:: ${tt.name}")
//            userFilePath.add(File(it.path!!))
//        }
//
//        val uploadResultPath = mutableListOf<Uri>()
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
//
//        val restaurantId = "testId"
//
//        fireStoreInstance
//            .collection("delivery")
//            .document("users")
//            .collection("user")
//            .get()
//            .addOnSuccessListener { result ->
//
//                val dayCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    LocalDate.now().toString()
//                } else {
//                    val simple = SimpleDateFormat("yyyy-M-dd", Locale.KOREA)
//                    val current = simple.format(Date())
//                    current
//                }
//                result.forEachIndexed { index, userData ->
//                    fireStoreInstance.collection("delivery")
//                        .document("reviews")
//                        .collection("review")
//                        .document("test")
//                        .collection(restaurantId)
//                        .document("review_$index")
//                        .set(
//                            Review(
//                                takePicture = null,
//                                rating = Random.nextDouble(2.3, 5.0).toFloat().number1Digits(),
//                                content = "테스트용 평가 내용입니다.",
//                                date = dayCheck,
//                                userId = userData.id,
//                                restaurantId = restaurantId
//                            )
//                        )
//                }
//            }
//
//        testImage.forEachIndexed { index, uri ->
//
//            fireStorageInstance.reference
//                .child("delivery_app/test/menu/${uri.lastPathSegment}.jpg")
//                .apply {
//                    putFile(uri)
//                        .continueWithTask { task ->
//                            if (!task.isSuccessful) {
//                                task.exception?.let { e->
//                                    throw e
//                                }
//                            }
//                            downloadUrl
//                        }
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                fireStoreInstance.collection("delivery")
//                                    .document("restaurantMenu")
//                                    .collection("test")
//                                    .document(restaurantId)
//                                    .collection("menu")
//                                    .document("menu_$index")
//                                    .set(
//                                        RestaurantMenu(
//                                            restaurantId = restaurantId,
//                                            imageUri = task.result.toString(),
//                                            menuName = "테스트 메뉴입니다.",
//                                            price = "12100 원",
//                                            detailContent = "메뉴를 위한 간단한 설명을 해 주세요."
//                                        )
//                                    )
//                            }
//                        }
//                }
//        }
//    }
}