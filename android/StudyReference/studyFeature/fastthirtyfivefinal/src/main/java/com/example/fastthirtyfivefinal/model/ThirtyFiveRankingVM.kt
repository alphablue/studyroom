package com.example.fastthirtyfivefinal.model

import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveRanking
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveProductDelegate

/**
 * 단점으로는 delegate 를 사용하게 되면 delegate 의 인터페이스에 정의된 함수들도 호출이 되는데,
 * 그렇다면 ThirtyFiveRankingVM 에서는 delegate 에서 구현해 줘야되는 함수가 많을 수록 실제 비지니스에 필요하지 않는
 * 함수들 까지 구현, 호출을 해야되는 것이 많아지는 단점이 있다.
 * */
class ThirtyFiveRankingVM(
    model: ThirtyFiveRanking,
    private val productDelegate: ThirtyFiveProductDelegate
): ThirtyFivePresentationVM<ThirtyFiveRanking>(model) {

    fun openRankingProduct(product: ThirtyFiveProduct) {
        productDelegate.openProduct(product)
        sendRankingLog()
    }

    private fun sendRankingLog() {

    }
}