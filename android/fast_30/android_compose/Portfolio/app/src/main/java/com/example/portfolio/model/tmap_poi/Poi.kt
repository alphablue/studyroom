package com.example.portfolio.model.tmap_poi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Poi(
    val buildingNo1: String,
    val buildingNo2: String,
    val dataKind: String,
    val detailAddrName: String,
    val evChargers: EvChargers,
    val firstNo: String,
    val frontLat: String,
    val frontLon: String,
    val ggPrice: String,
    val hhPrice: String,
    val highGgPrice: String,
    val highHhPrice: String,
    val highHhSale: String,
    val id: String,
    val llPrice: String,
    val lowerAddrName: String,
    val merchantFlag: String,
    val middleAddrName: String,
    val minOilYn: String,
    val mlClass: String,
    val name: String,
    val noorLat: String,
    val noorLon: String,
    val oilBaseSdt: String,
    val parkFlag: String,
    val pkey: String,
    val radius: String,
    val roadName: String,
    val rpFlag: String,
    val secondNo: String,
    val stId: String,
    val telNo: String,
    val upperAddrName: String
): Parcelable