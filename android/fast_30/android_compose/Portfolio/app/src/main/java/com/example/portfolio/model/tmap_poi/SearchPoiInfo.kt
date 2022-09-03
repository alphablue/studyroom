package com.example.portfolio.model.tmap_poi

data class SearchPoiInfo(
    val count: Int,
    val page: Int,
    val pois: Pois,
    val totalCount: Int
)