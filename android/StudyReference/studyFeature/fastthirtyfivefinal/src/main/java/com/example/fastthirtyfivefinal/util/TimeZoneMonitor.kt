package com.example.fastthirtyfivefinal.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.example.fastthirtyfivefinal.di.ApplicationScope
import com.example.fastthirtyfivefinal.di.Dispatcher
import com.example.fastthirtyfivefinal.di.FastDispatchers
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinTimeZone
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

interface TimeZoneMonitor {
    val currentTimeZone: Flow<TimeZone>
}

@Singleton
internal class TimeZoneBroadcastMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
    @ApplicationScope scope: CoroutineScope,
    @Dispatcher(FastDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : TimeZoneMonitor {
    override val currentTimeZone: Flow<TimeZone> =
        callbackFlow {
            // 시스템의 기본 타임 존을 보낸다.
            trySend(TimeZone.currentSystemDefault())

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent) {
                    if (intent.action != Intent.ACTION_TIMEZONE_CHANGED) return

                    val zoneIdFromIntent = if (VERSION.SDK_INT < VERSION_CODES.S) {
                        null
                    } else {
                        intent.getStringExtra(Intent.EXTRA_TIMEZONE)?.let { timeZoneId ->
                            val zoneId = ZoneId.of(timeZoneId, ZoneId.SHORT_IDS)

                            zoneId.toKotlinTimeZone()// java 형 timeZone 데이터를 kotlin 형 TimeZone 으로 변환
                        }
                    }

                    trySend(zoneIdFromIntent ?: TimeZone.currentSystemDefault())
                }
            }

            // 여기서 다시 한번더 보내는 이유는 브로드캐스트 리시버가 등록 하는데 최소한 수 millisec 정도는 필요로 하기 때문에 그 사이에
            // 이벤트를 놓치는 경우를 수정하기 위함이다.
            trySend(TimeZone.currentSystemDefault())

            awaitClose {
                context.unregisterReceiver(receiver)
            }
        }
            .distinctUntilChanged()
            .conflate()
            .flowOn(ioDispatcher)
            .shareIn(scope, SharingStarted.WhileSubscribed(5_000), 1)
}