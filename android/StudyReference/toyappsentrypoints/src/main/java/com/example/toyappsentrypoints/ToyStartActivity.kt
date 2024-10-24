package com.example.toyappsentrypoints

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.toyappsentrypoints.ui.theme.StudyReferenceTheme
import java.nio.charset.StandardCharsets

class ToyStartActivity : ComponentActivity() {
    private lateinit var intent: Intent
    private lateinit var pendingIntent: PendingIntent
    private lateinit var nfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_toy_nfc_start)
        setContent {
            StudyReferenceTheme {
                Scaffold { innerPadding ->
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    ) {

                    }
                }
            }
        }

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

//        nfcAdapter.enableReaderMode()
    }

    override fun onPause() {
        super.onPause()

        // NFC foreground Dispatch 해제
        if (::nfcAdapter.isInitialized) {
            nfcAdapter.disableForegroundDispatch(this)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // NFC 태그 읽기
        val nfcText = readNFCTag(intent)

        if (nfcText.isEmpty()) {
            Toast.makeText(this, "Read Nfc Tag: $nfcText", Toast.LENGTH_LONG).show()
        }
    }

    private fun readNFCTag(intent: Intent) : String {
        if(NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val ndef: Ndef? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Ndef.get(intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java))
            } else {
                Ndef.get(intent.getParcelableExtra(NfcAdapter.EXTRA_TAG))
            }

            ndef?.let {
                ndef.use {

                    try {
                        it.connect()
                        val ndefMessage = ndef.ndefMessage
                        val records = ndefMessage.records

                        if (records.isNotEmpty()) {
                            val payload = String(records.first().payload, StandardCharsets.UTF_8)
                            return payload
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {

                    }
                }

            }
        }
        return ""
    }
}