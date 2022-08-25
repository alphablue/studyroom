package com.example.portfolio.model.googlegeocode


import com.google.gson.annotations.SerializedName

data class GoogleGeoCode(
    @SerializedName("plus_code")
    val plusCode: PlusCode,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("status")
    val status: String
)