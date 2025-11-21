package com.example.studystartingpoint.ui.customCalendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.studystartingpoint.systemArch.dateModule.daysOfWeek
import com.example.studystartingpoint.util.d
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

@Composable
fun CalendarView() {
    val today = remember { LocalDate.now() } // yyyy-MM-dd
    val currentMonth = remember(today) { YearMonth.of(today.year, today.month) }
    val daysOfWeek = remember { daysOfWeek() }

    Box(
        Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                // 국가별로 각 달력에 무슨요일부터 시작을 하는지, 우리나라는 한 주의 시작을 일요일로 표시
                val a = WeekFields.of(Locale.getDefault()).firstDayOfWeek
                "a $a".d("calculateDate")

                // 각 달에서 첫 시작일이 언제인지를 date 객체로 가져옴, 요일등의 데이터가 포함되어 있기 때문에 계산에 용이함
                val thisMonthFirstDay = currentMonth.atDay(1)
                 "thisMonthFirstDay $thisMonthFirstDay".d("calculateDate")

                // 첫번째 주에 대한 계산
                // 달력에서 시작하는 요일(달력의 표기 규정에 관한 것)과 현재 월에서 첫 시작일의 요일 사이에 얼마만큼의 날짜가 있는지를 계산 하는 부분
                val inDay = a.daysUntil(thisMonthFirstDay.dayOfWeek)
                 "inDay $inDay ->  ${thisMonthFirstDay.dayOfWeek}".d("calculateDate")

                // 마지막 주에 대한 계산
                // 마지막 주에서 이번달이 끝나는 날짜가 달력 표기법에서 어느위치에 존재하는지를 확인하는 것, 0 이라면 표기법에서의 가장 마지막 날짜가 되고
                // 1 이상이라면 달력 표기법에서 추가로 다음달 날짜를 표시해 줘야 하는 날 을 나타낸 것이다.
                val outDay = (inDay + currentMonth.lengthOfMonth()).let { inDays ->
                    val endRowDay = if(inDays % 7 != 0) 7 - (inDays % 7) else 0
                    endRowDay
                }
                 "outDay $outDay -> 현재 월의 총 일수 : ${currentMonth.lengthOfMonth()}".d("calculateDate")

                val monthData = MonthDataSet(
                    month = currentMonth,
                    inDays = inDay,
                    outDays = outDay
                )

                for((row, week) in monthData.calendarWeekList.withIndex()) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        for ((column, day) in week.withIndex()) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clipToBounds()
                            ) {
                                DayView(day)
                            }
                        }
                    }
                }
            }
        }
    }
}

data class MonthDataSet(
    private val month: YearMonth,

    // 첫번째 주에 대한 계산
    // 달력에서 시작하는 요일(달력의 표기 규정에 관한 것)과 현재 월에서 첫 시작일의 요일 사이에 얼마만큼의 날짜가 있는지를 위한 계산 값
    private val inDays: Int,

    // 마지막 주에 대한 계산
    // 마지막 주에서 이번달이 끝나는 날짜가 달력 표기법에서 어느위치에 존재하는지를 확인하는 것, 0 이라면 표기법에서의 가장 마지막 날짜가 되고
    // 1 이상이라면 달력 표기법에서 추가로 다음달 날짜를 표시해 줘야 하는 날(date)을 나타낸 것이다.
    private val outDays: Int
) {
    private val totalDays = inDays + month.lengthOfMonth() + outDays

    // 첫주에서 달력의 표기 규정에 맞춰 계산된 첫번째 날의 date객체
    private val firstDateObj = month.atDay(1).minusDays(inDays.toLong())

    // 달력에서 표시할 총 주의 갯수
    private val rows = (0 until totalDays).chunked(7)

    val calendarWeekList = rows.map { rowWeek -> rowWeek.map { dayOffSet -> firstDateObj.plusDays(dayOffSet.toLong()) } }
}

@Composable
fun DayView(
    day: LocalDate
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RectangleShape),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = Color(0x4DFFFFFF)
        Text(
            text = day.dayOfMonth.toString(),
            color = textColor,
            fontSize = 15.sp
        )
    }
}

public fun DayOfWeek.daysUntil(other: DayOfWeek): Int = (7 + (other.ordinal - ordinal)) % 7

@Preview
@Composable
fun CalendarPreView() {
    CalendarView()
}

val YearMonth.nextMonth: YearMonth
    get() = this.plusMonths(1)

val YearMonth.previousMonth: YearMonth
    get() = this.minusMonths(1)