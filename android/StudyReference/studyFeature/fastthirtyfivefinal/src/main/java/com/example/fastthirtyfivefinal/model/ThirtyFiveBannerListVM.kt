package com.example.fastthirtyfivefinal.model

import com.example.fastthirtyfive_domain.model.ThirtyFiveBannerList
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveBannerDelegate

class ThirtyFiveBannerListVM(
    model: ThirtyFiveBannerList,
    private val bannerDelegate: ThirtyFiveBannerDelegate
): ThirtyFivePresentationVM<ThirtyFiveBannerList>(model) {

    fun openBannerList(bannerId: String) {
        bannerDelegate.openBanner(bannerId)
    }
}