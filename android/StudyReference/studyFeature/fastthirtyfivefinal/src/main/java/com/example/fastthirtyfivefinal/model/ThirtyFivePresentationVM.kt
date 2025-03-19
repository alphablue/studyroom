package com.example.fastthirtyfivefinal.model

import com.example.fastthirtyfive_domain.model.ThirtyFiveBaseModel

abstract class ThirtyFivePresentationVM<T : ThirtyFiveBaseModel>(
    val model: T
)