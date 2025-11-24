package com.example.studystartingpoint.ui.customCalendar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.studystartingpoint.systemArch.dateModule.daysOfWeek
import com.example.studystartingpoint.util.d
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

@Composable
fun CalendarView(
    containerInnerPadding: PaddingValues
) {
    var today by remember { mutableStateOf(LocalDate.now())  } // yyyy-MM-dd
    val currentMonth = remember(today) { YearMonth.of(today.year, today.month) }
    val daysOfWeek = remember { daysOfWeek() }

    // 국가별로 각 달력에 무슨요일부터 시작을 하는지, 우리나라는 한 주의 시작을 일요일로 표시
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

    // drag 오프셋
    var verticalDragOffset by remember { mutableFloatStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    val animatedOffset by animateFloatAsState(
        targetValue = if(isDragging) verticalDragOffset else 0f,
        label = "offsetAnimation"
    )
    val maxDragPx = 600f
    val draggableState = rememberDraggableState { delta ->
        verticalDragOffset = (verticalDragOffset - delta).coerceIn(0f, maxDragPx)
        "dragOffset $verticalDragOffset".d("calendarAnimation")
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(containerInnerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CalendarNormalHeader(
                currentMonth = currentMonth,
                days = daysOfWeek,
                previousEvent = {
                    today = today.minusMonths(1)
                },
                nextEvent = {
                    today = today.plusMonths(1)
                }
            )

            val minWeightFactor = 0.35f
            val maxWeightFactor = 1f

            val dragProgress = (animatedOffset / maxDragPx).coerceIn(0f, 1f)
            "드래그의 정도 확인 ${(animatedOffset / maxDragPx).coerceIn(0f, 1f)}".d("calculator")
            val currentRowWeight = lerp(maxWeightFactor, minWeightFactor, dragProgress)

            "동적 가중치 $currentRowWeight".d("calendarAnimation")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(currentRowWeight)
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = draggableState,
                        onDragStarted = { isDragging = true },
                        onDragStopped = { isDragging = false }
                    )
            ) {
                // 각 달에서 첫 시작일이 언제인지를 date 객체로 가져옴, 요일등의 데이터가 포함되어 있기 때문에 계산에 용이함
                val thisMonthFirstDay = currentMonth.atDay(1)
                 "thisMonthFirstDay $thisMonthFirstDay".d("calculateDate")

                // 첫번째 주에 대한 계산
                // 달력에서 시작하는 요일(달력의 표기 규정에 관한 것)과 현재 월에서 첫 시작일의 요일 사이에 얼마만큼의 날짜가 있는지를 계산 하는 부분
                val inDay = firstDayOfWeek.daysUntil(thisMonthFirstDay.dayOfWeek)
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

            if(1f - currentRowWeight > 0) {
                Spacer(
                    modifier = Modifier.weight(1f - currentRowWeight)
                )
            }
        }
    }
}

@Composable
fun CalendarNormalHeader(
    currentMonth: YearMonth,
    days: List<DayOfWeek>,
    previousEvent: () -> Unit,
    nextEvent: () -> Unit
) {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier
                    .size(40.dp),
                onClick = previousEvent
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
            }

            Text(
                text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                fontSize = 18.sp,
                color = Color(0xff100000)
            )

            IconButton(
                modifier = Modifier
                    .size(40.dp),
                onClick = nextEvent
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "")
            }
        }

        Row() {
            for(dayInfo in days) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = dayInfo.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    fontSize = 14.sp,
                    color = Color(0xFF589816),
                    textAlign = TextAlign.Center
                )
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
        val textColor = Color(0xFF1A0707)
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
    CalendarView(PaddingValues(10.dp))
}

val YearMonth.nextMonth: YearMonth
    get() = this.plusMonths(1)

val YearMonth.previousMonth: YearMonth
    get() = this.minusMonths(1)