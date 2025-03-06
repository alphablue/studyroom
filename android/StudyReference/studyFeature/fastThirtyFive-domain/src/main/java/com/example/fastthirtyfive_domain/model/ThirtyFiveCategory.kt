package com.example.fastthirtyfive_domain.model

import kotlinx.serialization.Serializable


@Serializable
data class ThirtyFiveCategory(
    val categoryId: String,
    val categoryName: String,
)
{

//    @Serializer(forClass = ThirtyFiveCategory::class)
//    companion object : JsonContentPolymorphicSerializer<ThirtyFiveCategory>(ThirtyFiveCategory::class) {
//        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ThirtyFiveCategory> {
//            return when (element.jsonObject["categoryId"]?.jsonPrimitive?.content) {
//                "1" -> Top.serializer()
//                "2" -> Outerwear.serializer()
//                "3" -> Dress.serializer()
//                "4" -> Pants.serializer()
//                "5" -> Skirt.serializer()
//                "6" -> Shoes.serializer()
//                "7" -> Bag.serializer()
//                "8" -> FashionAccessories.serializer()
//                else -> Top.serializer()
//            }
//        }
//    }
}

val Top = ThirtyFiveCategory("1", "상의")
val Outerwear = ThirtyFiveCategory("2", "아우터")
val Dress = ThirtyFiveCategory("3", "원피스")
val Pants = ThirtyFiveCategory("4", "바지")
val Skirt = ThirtyFiveCategory("5", "치마")
val Shoes = ThirtyFiveCategory("6", "신발")
val Bag = ThirtyFiveCategory("7", "가방")
val FashionAccessories = ThirtyFiveCategory("8", "패션잡화")


// TODO : 이렇게 실드 클래스의 정의를 활용해서 serialization 기능을 활용 하려고 하는데 실드 클래스의 사용 이유와 아래의 사례에서 해당 기능을 사용 할 수 있는지 확인 필요
//@Serializable
//sealed class ThirtyFiveCategory(
//    val categoryId: String,
//    val categoryName: String,
//)
//{
//    data object Top : ThirtyFiveCategory("1", "상의")
//    data object Outerwear : ThirtyFiveCategory("2", "아우터")
//    data object Dress : ThirtyFiveCategory("3", "원피스")
//    data object Pants : ThirtyFiveCategory("4", "바지")
//    data object Skirt : ThirtyFiveCategory("5", "치마")
//    data object Shoes : ThirtyFiveCategory("6", "신발")
//    data object Bag : ThirtyFiveCategory("7", "가방")
//    data object FashionAccessories : ThirtyFiveCategory("8", "패션잡화")
//
//    @Serializer(forClass = ThirtyFiveCategory::class)
//    companion object : JsonContentPolymorphicSerializer<ThirtyFiveCategory>(ThirtyFiveCategory::class) {
//        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ThirtyFiveCategory> {
//            return when (element.jsonObject["categoryId"]?.jsonPrimitive?.content) {
//                "1" -> Top.serializer()
//                "2" -> Outerwear.serializer()
//                "3" -> Dress.serializer()
//                "4" -> Pants.serializer()
//                "5" -> Skirt.serializer()
//                "6" -> Shoes.serializer()
//                "7" -> Bag.serializer()
//                "8" -> FashionAccessories.serializer()
//                else -> Top.serializer()
//            }
//        }
//    }
//}