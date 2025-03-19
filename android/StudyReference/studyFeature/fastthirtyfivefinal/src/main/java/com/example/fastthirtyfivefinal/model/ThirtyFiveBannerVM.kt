package com.example.fastthirtyfivefinal.model

import com.example.fastthirtyfive_domain.model.ThirtyFiveBanner
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveBannerDelegate

class ThirtyFiveBannerVM(
    model: ThirtyFiveBanner,
    bannerDelegate: ThirtyFiveBannerDelegate
): ThirtyFivePresentationVM<ThirtyFiveBanner>(model), ThirtyFiveBannerDelegate by bannerDelegate