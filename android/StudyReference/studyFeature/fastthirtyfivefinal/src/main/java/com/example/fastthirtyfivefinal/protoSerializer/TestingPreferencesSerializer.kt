package com.example.fastthirtyfivefinal.protoSerializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.fastthirtyfivefinal.TestingPreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class TestingPreferencesSerializer @Inject constructor() : Serializer<TestingPreferences> {
    override val defaultValue: TestingPreferences
        get() = TestingPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TestingPreferences {
        try {
            return TestingPreferences.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: TestingPreferences, output: OutputStream) {
        t.writeTo(output)
    }

}