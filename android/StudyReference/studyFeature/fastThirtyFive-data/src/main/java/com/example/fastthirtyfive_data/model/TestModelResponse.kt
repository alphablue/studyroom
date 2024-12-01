package com.example.fastthirtyfive_data.model

import com.example.fastthirtyfive_domain.model.TestModel

/**
 *  가능하면 서버에서 받는 부분은 항상 모든 값을 넘겨준다고 생각하지 말자
 *  참고 : 데이터 레이어는 domain 레이어를 참조 할 수 있지만 domain 레이어는 data layer 를 참조 할 수 없다
 * */
class TestModelResponse(
    val name: String?
) {

}

// 도메인 모델로 변환 하는 코드
fun TestModelResponse.toDomainModel(): TestModel? {
    if(name != null) {
        return TestModel(name)
    }
    return null
}