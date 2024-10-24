package com.example.toyappsentrypoints

import android.nfc.cardemulation.HostApduService
import android.os.Bundle

class HCEServiceImplement: HostApduService() {

    // 반이중 방식, 리더기 -> HCE 로 APDU 를 한번 보내고, 이후 HCE -> 리더기 로 응답 APDU 가 올때까지 대기 한다.
    // 본래 여러 논리 채널에서 병렬 교환이 가능하지만, 안드로이드에서는 단일 논리 채널만 지원하므로 하나의 APDU 교환만 일어난다.
    // main tread 에서 수행됨
    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        TODO("Not yet implemented")

        // 필요 작업이 끝났다면 아래 함수를 통해 리더기로 데이터 전송이 가능함
        // 참조 : https://developer.android.com/reference/android/nfc/cardemulation/HostApduService#sendResponseApdu(byte%5B%5D)
//        sendResponseApdu()
    }

    //
    override fun onDeactivated(reason: Int) {
        TODO("Not yet implemented")
//        CardEmulation.CATEGORY_PAYMENT
    }

}