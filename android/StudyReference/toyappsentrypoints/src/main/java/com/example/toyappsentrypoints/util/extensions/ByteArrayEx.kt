package com.example.toyappsentrypoints.util.extensions

// kotlin 1.9.0 이후 버전에서 제공해 주는 toHexString을 실험 옵션이 빠지면 사용하면 좋을 것 같다.
// 2024. 9. 11 기준 동일한 작업 방식을 보여줌
fun ByteArray.toHexStringEx(): String {
    return toHexStringEx(0, this.size)
}

fun ByteArray.toHexStringEx(offset: Int, length: Int) : String {
    val sb = StringBuilder()
    for (i in offset until (offset + length)) {
        val b = this[i]
        val octet = b.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        sb.append(HEX_CHARS[firstIndex])
        sb.append(HEX_CHARS[secondIndex])
    }
    return sb.toString()
}
private val HEX_CHARS = "0123456789ABCDEF".toCharArray()