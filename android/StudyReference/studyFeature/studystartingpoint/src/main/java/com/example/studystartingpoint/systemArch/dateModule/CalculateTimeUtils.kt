package com.example.studystartingpoint.systemArch.dateModule

import android.content.Context
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.example.studystartingpoint.util.d
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * localDate -> 년/월/일 의 정보가 궁금할 때 사용
 * localDateTime -> 년/월/일 시/분/초 의 정보가 궁금할 때 사용
 * */
fun getCalenderInfo(
    context: Context,
    isTest: Boolean = true
) {
    if(isTest) {
        exampleCalculateJavaTimeClass(context)
    } else {
        // calendar 나 java date 클래스 대신 time 클래스가 표준으로 잡힘
        val firstDayOfWeek = WeekFields.of(getPrimaryLocale(context.resources)).firstDayOfWeek

//    // 2. 해당 월의 첫 날 (예: 11월 1일)
//    val startOfMonth = YearMonth.of()
//
//    // 3. 11월 1일이 주의 시작 요일로부터 며칠 떨어져 있는지 계산 (이전 달 날짜 채우기용)
//    // 예: 일요일 시작인데 1일이 수요일이면, 앞의 일, 월, 화 3칸이 필요함
//    val daysFromStartOfWeek = DayOfWeek.values().let { days ->
//        // 요일 순서를 로케일에 맞게 재배열하여 계산
//        val pivot = days.indexOf(firstDayOfWeek)
//        val current = days.indexOf(startOfMonth.dayOfWeek)
//        (current - pivot + 7) % 7
//    }
//
//    // 4. 캘린더 그리드 시작 날짜 계산 (이전 달의 날짜 포함)
//    val firstDateOfGrid = startOfMonth.minusDays(daysFromStartOfWeek.toLong())
//
//    // 5. 42개(6주 x 7일)의 날짜 리스트 생성
//    return (0 until 42).map { dayOffset ->
//        firstDateOfGrid.plusDays(dayOffset.toLong())
//    }
    }
}

private fun exampleCalculateJavaTimeClass(context: Context) {
    /**
     * 주의
     * LocalDate 는 yyyy-MM-dd 데이터만 가지고 있고
     * LocalTime 은 hh:MM:ss 데이터만 가지고 있으며
     * LocalDateTime 은 날짜와 시간을 모두 가지고 있음
     * 각 데이터의 형태가 뭔지 확인 하고 날짜를 조작 해야 할 필요가 있음
     * */

    val today = LocalDate.now() // yyyy-MM-dd
    """\n
        | [Local Date] 현재시간 :${today}
        | [Local Date] 내일 :${today.plusDays(1) }
        | [Local Date] 일주일 뒤 :${today.plusWeeks(1) }
        | [Local Date] 3달 전 :${today.minusMonths(1) }
        | [Local Date] 1년 뒤 :${today.plusYears(1) }
        | 
    """.trimMargin().d("calculateDate")

    val now = LocalTime.now() // hh:mm:ss
    """\n
        | [Local Date Time] 현재 시간(시/분) : $now
        | [Local Date Time] 현재 30분 후 : ${now.plusMinutes(30)}
        | [Local Date Time] 현재 2시간 후 : ${now.plusHours(2)}
        | [Local Date Time] 현재 30초 전 : ${now.minusSeconds(30)}
    """.trimIndent().d("calculateDate")

    val date = LocalDate.of(2024, 2, 10) // 2024년은 윤년
    """\n
       | [Local Date Time] 윤년 확인 : ${date.isLeapYear}
       | [Local Date Time] 이번 달(2월)의 총 일수 : ${date.lengthOfMonth()}
       | [Local Date Time] 1년의 총 일수 : ${date.lengthOfYear()}
    """.trimIndent().d("calculateDate")

    val birthday = LocalDate.of(1995, 5, 20)
    val period = Period.between(birthday, today)
    // 윤년이 포함된 날짜
    val startDate = LocalDate.of(2024, 1, 1)
    val endDate = LocalDate.of(2024, 12, 31)

    // 시간 계산
    val startTime = LocalDateTime.of(2025, 11, 19, 10, 0, 0)
    val endTime = LocalDateTime.of(2025, 11, 19, 12, 30, 0)
    val duration = Duration.between(startTime, endTime)
    """\n
       | [차이 계산] 생년 월일 : $birthday
       | [차이 계산] 나이 : ${period.years}
       | [차이 계산] 태어난 날로부터 : ${period.months}개월 , ${period.days}일 지남
       | [차이 계산] 총 며칠 지났어?(24년 윤년) : ${ChronoUnit.DAYS.between(startDate, endDate)}일
       | [차이 계산] 총 분 or 초 지났어? : ${duration.toMinutes()}분, ${duration.seconds}초
    """.trimIndent().d("calculateDate")

    """\n
       | [이번 달 에서...] 1일 : ${today.with(TemporalAdjusters.firstDayOfMonth())}
       | [이번 달 에서...] 마지막 날 : ${today.with(TemporalAdjusters.lastDayOfMonth())}
       | [이번 달 에서...] 다음달 1일 : ${today.with(TemporalAdjusters.firstDayOfNextMonth())}
       | [이번 달 에서...] 첫 번째 월요일 : ${today.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY))}
       | [이번 달 에서...] 이후 가장 가까운 금요일 : ${today.with(TemporalAdjusters.next(DayOfWeek.FRIDAY))}
       | [이번 달 에서...] 포함 가장 가까운 일요일 : ${today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))}
    """.trimIndent().d("calculateDate")

    // 이번 주의 시작일 구하기(나라별로 다름, 월요일 이냐 일요일 이냐)
    val weekFields = WeekFields.of(getPrimaryLocale(context.resources))
    """\n
       | [주의 시작 계산] 요일 체계 : ${weekFields.firstDayOfWeek}
       | [주의 시작 계산] 1번째 주의 시작 날짜 : ${today.with(weekFields.dayOfWeek(), 1)}
       | [주의 시작 계산] 1번째 주의 마지막 날짜 : ${today.with(weekFields.dayOfWeek(), 1).plusDays(6)}
    """.trimIndent().d("calculateDate")

    // 주의, 로컬에서 제공하지 않는 문자가 포함되면 에러남
//    val formatter1 = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh:mm", getPrimaryLocale(context.resources))
    val now2 = LocalDateTime.now()
    val formatter1 = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh:mm", Locale.KOREA)
    """\n
       | [문자열 포맷팅] 커스텀 포맷 : ${now2.format(formatter1)}
       | [문자열 포맷팅] iso 표준 포맷(서버전송) : ${now2.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)}
       | [문자열 포맷팅] 문자열 -> 날짜 : ${LocalDateTime.parse("2025년 08월 14일 오전 10:22", formatter1)}
    """.trimIndent().d("calculateDate")
}

fun getPrimaryLocale(resources: Resources): Locale {
    return ConfigurationCompat.getLocales(resources.configuration).get(0) ?: Locale.getDefault()
}