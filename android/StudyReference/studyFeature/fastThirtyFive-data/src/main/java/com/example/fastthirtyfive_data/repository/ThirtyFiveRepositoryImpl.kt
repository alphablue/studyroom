package com.example.fastthirtyfive_data.repository

import android.content.Context
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.repository.ThirtyFiveRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import java.io.InputStreamReader
import javax.inject.Inject

class ThirtyFiveRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ThirtyFiveRepository {
    override fun getProductList(): Flow<List<ThirtyFiveProduct>> = flow {
        val inputStream = context.assets.open("product_list.json")
        val inputStreamReader = InputStreamReader(inputStream)
        val jsonString = inputStreamReader.readText()

        // gson 을 사용하는 것과 kotlin serializer 를 사용하는 건 다름
        // 아래는 gson을 사용한 방식
//        val type = object: TypeToken<List<ThirtyFiveProduct>>() {}.type
//        return GsonBuilder().create().fromJson(jsonString, type)

        val jsonElement = Json.parseToJsonElement(jsonString)

        emit(Json.decodeFromJsonElement(jsonElement))
    }
}