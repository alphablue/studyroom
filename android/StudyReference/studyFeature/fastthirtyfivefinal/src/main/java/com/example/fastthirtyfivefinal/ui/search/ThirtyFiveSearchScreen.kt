package com.example.fastthirtyfivefinal.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchFilter
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveProductCard
import com.example.fastthirtyfivefinal.ui.theme.Purple200
import com.example.fastthirtyfivefinal.ui.theme.Purple500
import com.example.fastthirtyfivefinal.viewmodel.search.ThirtyFiveSearchViewModel
import kotlinx.coroutines.launch

@Composable
fun ThirtyFiveSearchScreen(
    navigationController: NavHostController,
    viewModel: ThirtyFiveSearchViewModel = hiltViewModel()
) {
    val searchFilter by viewModel.searchFilters.collectAsState()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()
    var currentFilterType by remember { mutableStateOf<ThirtyFiveSearchFilter.ThirtyFiveType?>(null) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp),
        sheetContent = {
            when(currentFilterType) {
                ThirtyFiveSearchFilter.ThirtyFiveType.CATEGORY -> {
                    val categoryFilter = searchFilter.first { it is ThirtyFiveSearchFilter.CategoryFilter } as ThirtyFiveSearchFilter.CategoryFilter

                    ThirtyFiveSearchFilterCategoryContent(filter = categoryFilter) {
                        scope.launch {
                            currentFilterType = null
                            sheetState.collapse()
                        }
                        viewModel.updateFilter(it)
                    }
                }

                ThirtyFiveSearchFilter.ThirtyFiveType.PRICE -> {
                    val priceFilter = searchFilter.first { it is ThirtyFiveSearchFilter.PriceFilter } as ThirtyFiveSearchFilter.PriceFilter

                    ThirtyFiveSearchFilterPriceContent(filter = priceFilter) {
                        scope.launch {
                            currentFilterType = null
                            sheetState.collapse()
                        }
                        viewModel.updateFilter(it)
                    }
                }

                else -> {}
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        ThirtyFiveSearchContent(viewModel, navigationController) {
            scope.launch {
                currentFilterType = it
                sheetState.expand()
            }
        }
    }
}

@Composable
fun ThirtyFiveSearchFilterCategoryContent(filter: ThirtyFiveSearchFilter.CategoryFilter, onCompleteFilter: (ThirtyFiveSearchFilter) -> Unit) {
    // 헤더
    // 카테고리 리스트

    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(300.dp)
    ) {
        Text(
            text = "카테고리 필터",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp),
        ) {
            items(filter.categories.size) { idx ->
                val category = filter.categories[idx]

                Button(
                    onClick = {
                        filter.selectedCategory = category
                        onCompleteFilter(filter)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (filter.selectedCategory == category) Purple500 else Purple200
                    )
                ) {
                    Text(fontSize = 18.sp, text = category.categoryName)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThirtyFiveSearchFilterPriceContent(filter: ThirtyFiveSearchFilter.PriceFilter, onCompleteFilter: (ThirtyFiveSearchFilter) -> Unit) {
    var sliderValues by remember {
        val selectedRange = filter.selectedRange

        if (selectedRange == null) {
            mutableStateOf(filter.priceRange.first..filter.priceRange.second)
        } else {
            mutableStateOf(selectedRange.first..selectedRange.second)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(300.dp)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "가격 필터",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    filter.selectedRange = sliderValues.start to sliderValues.endInclusive
                    onCompleteFilter(filter)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple200)
            ) {
                Text(
                    fontSize = 18.sp,
                    text = "완료"
                )
            }
        }

        RangeSlider(
            value = sliderValues,
            onValueChange = {
                sliderValues = it
            },
            valueRange = filter.priceRange.first..filter.priceRange.second,
            steps = 9 // 구간에 대해서 자동으로 설정 해주는 것?
        )
        Text(text = "최저가 ${sliderValues.start} ~ 최고가 : ${sliderValues.endInclusive}")
    }
}

@Composable
fun ThirtyFiveSearchContent(
    viewModel: ThirtyFiveSearchViewModel,
    navigationController: NavHostController,
    openFilterDialog: (ThirtyFiveSearchFilter.ThirtyFiveType) -> Unit // bottom 에서 올라오는 dialog
) {
    val searchResult by viewModel.searchResult.collectAsState()
    val searchFilters by viewModel.searchFilters.collectAsState()
    val searchKeyword by viewModel.searchKeywords.collectAsState(listOf())
    var keyword by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current // 키보드 관련 설정 하는 기능

    Column {
        // 검색바
        // 검색 결과 or 최근 검색어.
        ThirtyFiveSearchBox(
            keyword,
            onValueChange = { keyword = it },
            searchAction = {
                viewModel.search(keyword)
                keyboardController?.hide()
            }
        )

        if (searchResult.isEmpty()) {
            Text(
                modifier = Modifier.padding(6.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = "최근 검색어"
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(searchKeyword.size) { idx ->
                    val currentKeyword = searchKeyword.reversed()[idx].keyword

                    Button(
                        onClick = {
                            keyword = currentKeyword
                            viewModel.search(keyword)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Unspecified)
                    ) {
                        Text(
                            fontSize = 18.sp,
                            text = currentKeyword
                        )
                    }
                }
            }
        } else { // 검색 결과가 있는 경우
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Button(
                    onClick = { openFilterDialog(ThirtyFiveSearchFilter.ThirtyFiveType.CATEGORY) }
                ) {
                    val filter = searchFilters.find { it.type == ThirtyFiveSearchFilter.ThirtyFiveType.CATEGORY } as? ThirtyFiveSearchFilter.CategoryFilter

                    if(filter?.selectedCategory == null) {
                        Text("Category")
                    } else {
                        Text("${filter.selectedCategory?.categoryName}")
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = { openFilterDialog(ThirtyFiveSearchFilter.ThirtyFiveType.PRICE) }
                ) {
                    val filter = searchFilters.find { it.type == ThirtyFiveSearchFilter.ThirtyFiveType.PRICE } as? ThirtyFiveSearchFilter.PriceFilter

                    if(filter?.selectedRange == null) {
                        Text("Category")
                    } else {
                        Text("${filter.selectedRange?.first} ~ ${filter.selectedRange?.second}")
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(searchResult.size) { idx ->
                    ThirtyFiveProductCard(navigationController, searchResult[idx])
                }
            }
        }
    }
}

@Composable
fun ThirtyFiveSearchBox(
    keyword: String,
    onValueChange: (String) -> Unit,
    searchAction: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = keyword,
            onValueChange = onValueChange,
            placeholder = { Text(text = "검색어를 입력해주세요.") },
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words, // 대문자, 소문자가 있는 언어에서 대문자로 변환 시키는 옵션
                autoCorrectEnabled = true // 자동 수정 기능을 제공할 것인가에 대한 부분
            ),
            keyboardActions = KeyboardActions(onSearch = { searchAction() }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            maxLines = 1,
            singleLine = true, // maxLine 과 , singleLine의 적용이 기기마다 둘중 하나만 적용되는 경우가 있어 두개다 넣어주는게 안전하다.
            leadingIcon = { Icon(Icons.Filled.Search, "SearchIcon") }
        )
    }
}