package com.example.fastthirtyfivefinal.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.credentials.GetCredentialRequest
import com.example.fastthirtyfivefinal.ui.theme.StudyReferenceTheme
import com.example.fastthirtyfivefinal.util.TimeZoneMonitor
import com.example.fastthirtyfivefinal.viewmodel.MainViewModelOld
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * 프레젠테이션 레이어 이다.
 * */
@AndroidEntryPoint
class FastThirtyFiveFinalActivity : ComponentActivity() {

//    private val viewModel: TempViewModel by viewModels()
    private val thirtyFiveMainViewModel: MainViewModelOld by viewModels()

    @Inject
    lateinit var timeZoneMonitor: TimeZoneMonitor

    @Inject
    lateinit var googleSignInRequester: GetCredentialRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        enableEdgeToEdge()
        setContent {

            /**
             * Scroll 가능한 picker 뷰
             * */
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                val values = remember { (1..99).map { it.toString() }}
//                val valuesPickerState = rememberPickerState()
//                val units = remember { listOf("seconds", "minutes", "hours") }
//                val unitsPickerState = rememberPickerState()
//
//                Text(text = "example picker", modifier = Modifier.padding(top = 16.dp))
//
//                Row(modifier = Modifier.fillMaxWidth()) {
//                    PickerView(
//                        state = valuesPickerState,
//                        items = values,
//                        visibleItemsCount = 3,
//                        modifier = Modifier.weight(0.3f),
//                        textModifier = Modifier.padding(8.dp),
//                        textStyle = TextStyle(fontSize = 32.sp)
//                    )
//
//                    PickerView(
//                        state = unitsPickerState,
//                        items = units,
//                        visibleItemsCount = 3,
//                        modifier = Modifier.weight(0.7f),
//                        textModifier = Modifier.padding(8.dp),
//                        textStyle = TextStyle(fontSize = 32.sp)
//                    )
//                }
//            }

            // material 3 컴포넌트 적용
//            ShowNewVersion(timeZoneMonitor)

            // material 2 컴포넌트 적용
            ShowOldVersion(
                googleSignInRequester,
                Firebase.auth,
                activityContext = this
            )
        }
        // 화면의 사이즈가 다양한데 변화에 맞게 보여줄 아이템의 갯수를 조절하기 위한 부분
        thirtyFiveMainViewModel.updateColumnCount(getColumnCount())
    }

    private fun getColumnCount(): Int {
        return getDisplayWidthDp().toInt() / DEFAULT_COLUMN_SIZE
    }

    // 현재 디스플레이의 dp 값을 가져오는 방법
    // window manager 를 통해서 가져오는 방법도 있지만 deprecated 되었다.
    private fun getDisplayWidthDp(): Float {
        return resources.displayMetrics.run { widthPixels / density }
    }

    companion object {
        private const val DEFAULT_COLUMN_SIZE = 160
    }
}

@Composable
fun ShowOldVersion(
    googleSignInRequester: GetCredentialRequest,
    firebaseAuth: FirebaseAuth,
    activityContext: Context
) {
    StudyReferenceTheme {
        MainScreenOlder(
            googleSignInRequester,
            firebaseAuth,
            activityContext
        )
    }
}

@Composable
fun ShowNewVersion(
    timeZoneMonitor: TimeZoneMonitor
) {
    StudyReferenceTheme {
        EntryThirtyFiveAppNew(
            appState = rememberFiveAppState(
                timeZoneMonitor
            )
        )
    }
}

@Composable
fun CustomUnderLineText() {
    val customText = buildAnnotatedString {
        append("이건 ")
//        withAnnotation("테스트", annotation = )
        append("를 위한 텍스트 정보 입니다.")
    }
}

@Composable
fun PickerView(
    items: List<String>,
    state: PickerState,
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    dividerColor: Color = LocalContentColor.current
) {
    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = Int.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex = listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex

    fun getItem(index: Int) = items[index % items.size]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState =  listState)

    val itemHeightPixels = remember { mutableIntStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.intValue)

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item -> state.selectedItem = item}
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * visibleItemsCount)
                .fadingEdge(fadingEdgeGradient)
        ) {
            items(listScrollCount) {idx ->
                Text(
                    text = getItem(index = idx),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = textStyle,
                    modifier = Modifier
                        .onSizeChanged { size -> itemHeightPixels.intValue = size.height }
                        .then(textModifier)
                )
            }
        }

        Divider(
            color = dividerColor,
            modifier = Modifier.offset(y = itemHeightDp * visibleItemsMiddle)
        )

        Divider(
            color = dividerColor,
            modifier = Modifier.offset(y = itemHeightDp * (visibleItemsMiddle + 1))
        )
    }
}

fun Modifier.fadingEdge(brush: Brush) =
    graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        .drawWithContent {
            drawContent()
            drawRect(brush = brush, blendMode = BlendMode.DstIn)
        }

@Composable
fun rememberPickerState() = remember { PickerState() }

@Composable
fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

class PickerState {
    var selectedItem by mutableStateOf("")
}