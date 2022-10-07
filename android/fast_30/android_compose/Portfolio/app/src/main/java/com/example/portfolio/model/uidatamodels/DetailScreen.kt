package com.example.portfolio.model.uidatamodels

data class Review(
    var takePicture: String = "",
    var rating: String = "",
    var content: String = "",
    var date: String = "",
    var userId: String = "",
    var restaurantId: String = "",
)

data class RestaurantMenu(
    var restaurantId: String? = "",
    var image: String? = "",
    var menuName: String? = "",
    var price: String? = "",
    var detailContent: String? = ""
)