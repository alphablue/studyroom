package com.example.fastthirtyfive_data.datasource

import android.content.Context
import com.example.fastthirtyfive_domain.model.ThirtyFiveBanner
import com.example.fastthirtyfive_domain.model.ThirtyFiveBannerList
import com.example.fastthirtyfive_domain.model.ThirtyFiveBaseModel
import com.example.fastthirtyfive_domain.model.ThirtyFiveCarousel
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveRanking
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.io.InputStreamReader
import javax.inject.Inject

class ThirtyFiveProductDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getHomeComponents(): Flow<List<ThirtyFiveBaseModel>> = flow {
//        val inputStream = context.assets.open("study_thirty_five_product_list.json")
        val inputStream = context.assets.open("clip31_product_list.json.json")
        val inputStreamReader = InputStreamReader(inputStream)
        val jsonString = inputStreamReader.readText()

        // gson 을 사용하는 것과 kotlin serializer 를 사용하는 건 다름
        // 아래는 gson을 사용한 방식
//        val type = object: TypeToken<List<ThirtyFiveProduct>>() {}.type
//        return GsonBuilder().create().fromJson(jsonString, type)

//        val jsonElement = Json.parseToJsonElement(jsonString)

        val jsonBuilder = Json {
            prettyPrint = true

            /**
             * 주의할 내용은 "type" 이라는 속성을 기본 구분자로 사용하고 있어 나중에 속성 설정에 주의해야 한다.
             * 구분자를 다른 것으로 하고 싶다면 수동으로 설정을 할 수 있다.
             * */

            /**
             * 주의할 내용은 "type" 이라는 속성을 기본 구분자로 사용하고 있어 나중에 속성 설정에 주의해야 한다.
             * 구분자를 다른 것으로 하고 싶다면 수동으로 설정을 할 수 있다.
             * */
//            classDiscriminator = "type"

            /**
             * 설정 할 때 주의 할 것이 기준이 되는 속성이 @SerialName 으로 구분되는 경우 해당 클래스에 프로퍼티로 존재하면 안된다.
             * @SerialName 로 구분하지 않는 경우 속성에 대한 프로퍼티가 존재 하더라도 파싱이 되지만 나머지 클래스에 대해서는 존재해서는 안된다.
             * 아래의 경우
             * ThirtyFiveBanner 는 @SerialName 를 활용해 구분하기 때문에 기준이되는 프로퍼티가 존재하면 안되지만
             * ThirtyFiveProduct 의 경우는 베이스가 되는 것이기 때문에 해당 속성의 프로퍼티가 있더라도 파싱은 가능하다.
             * */

            /**
             * 설정 할 때 주의 할 것이 기준이 되는 속성이 @SerialName 으로 구분되는 경우 해당 클래스에 프로퍼티로 존재하면 안된다.
             * @SerialName 로 구분하지 않는 경우 속성에 대한 프로퍼티가 존재 하더라도 파싱이 되지만 나머지 클래스에 대해서는 존재해서는 안된다.
             * 아래의 경우
             * ThirtyFiveBanner 는 @SerialName 를 활용해 구분하기 때문에 기준이되는 프로퍼티가 존재하면 안되지만
             * ThirtyFiveProduct 의 경우는 베이스가 되는 것이기 때문에 해당 속성의 프로퍼티가 있더라도 파싱은 가능하다.
             * */
            serializersModule = SerializersModule {
                polymorphic(ThirtyFiveBaseModel::class) {
                    subclass(ThirtyFiveBanner::class)
                    subclass(ThirtyFiveBannerList::class)
                    subclass(ThirtyFiveCarousel::class)
                    subclass(ThirtyFiveRanking::class)
                    defaultDeserializer { ThirtyFiveProduct.serializer() }
                }
            }
        }
        /**
         *  json element 객체로 변경을 하게 되면 제대로 파싱이 되지 않는 현상이 있다. string 기준으로
         *  polymorphic 적용을 할 수 있도록 하자
         * */
//        val jsonElement = jsonBuilder.parseToJsonElement(jsonString)
//        emit(Json.decodeFromJsonElement(jsonElement))
        emit(jsonBuilder.decodeFromString(jsonString))
    }

    fun getProducts(): Flow<List<ThirtyFiveProduct>> = getHomeComponents().map { it.filterIsInstance<ThirtyFiveProduct>() }
}

/**
 * Gson 을 활용하는 경우 아래 처럼 직접 설정이 필요하다.
 * */
//class BaseModelDeserializer : JsonDeserializer<BaseModel> {
//    companion object {
//        private const val TYPE = "type"
//    }
//
//    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): BaseModel {
//        val root = json?.asJsonObject
//        val gson = GsonBuilder().create()
//
//        val typeString = root?.get(TYPE)?.asString ?: ""
//
//        return when(ModelType.valueOf(typeString)) {
//            ModelType.BANNER -> gson.fromJson(root, Banner::class.java)
//            ModelType.PRODUCT -> gson.fromJson(root, Product::class.java)
//            ModelType.BANNER_LIST -> gson.fromJson(root, BannerList::class.java)
//            ModelType.CAROUSEL -> gson.fromJson(root, Carousel::class.java)
//            ModelType.RANKING -> gson.fromJson(root, Ranking::class.java)
//        }
//    }
//}