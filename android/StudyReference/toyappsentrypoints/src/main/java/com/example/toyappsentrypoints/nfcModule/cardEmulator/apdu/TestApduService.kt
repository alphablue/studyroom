@file:OptIn(ExperimentalStdlibApi::class)

package com.example.toyappsentrypoints.nfcModule.cardEmulator.apdu

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import com.example.toyappsentrypoints.util.d
import com.example.toyappsentrypoints.util.extensions.toHexStringEx
import com.example.toyappsentrypoints.util.getDeviceName

class TestApduService: HostApduService() {
    private lateinit var deviceName: String

    override fun onCreate() {
        super.onCreate()
        deviceName = getDeviceName()
    }

    private var messageCounter = 0
    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
//        "#ProcessCommandAdpu() ${commandApdu?.toHexString(HexFormat.UpperCase)}".d()
        "#ProcessCommandAdpu() ${commandApdu?.toHexStringEx()}".d()
        return commandApdu ?: ByteArray(2)
    }

    override fun onDeactivated(reason: Int) {
        TODO("Not yet implemented")
    }

}