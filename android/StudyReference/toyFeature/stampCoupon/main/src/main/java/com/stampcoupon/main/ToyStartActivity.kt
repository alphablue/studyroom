package com.stampcoupon.main

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ToyStartActivity : AppCompatActivity() {
    private lateinit var intent: Intent
    private lateinit var pendingIntent: PendingIntent
    private lateinit var nfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_toy_start)

        // nfc adapter 초기화
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        // pendingIntent 설정
        intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun onResume() {
        super.onResume()

        // NFC Foreground Dispatch 등록
        if(::nfcAdapter.isInitialized) {
            nfcAdapter.disableForegroundDispatch(this)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // NFC 태그 읽기
        val nfcText = readNFCTag(intent)
    }

    fun readNFCTag(intent: Intent) {
        if(NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
//            val ndef = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                Ndef.get(intent. getParcelableExtra((NfcAdapter.EXTRA_TAG, Tag::class.java))
//            } else {
////                intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)?.run {
////
////                }
////                Ndef.get( as Tag)
//                // TODO 작업 필요
//            }
        }
    }
}