package com.example.toss.next.sampleproject01datalayer.model

import com.example.seojin.sampleproject01domainlayer.model.TestModel

class TestModelResponse(val name: String?)

// 아래처럼 한번더 거치는 경우는 서버에서 모든 데이터가 안 왔을 수도 있기 때문에 안정성을 위한 과정이다.
fun TestModelResponse.toDomainModel(): TestModel? {
    if(name != null) {
        return TestModel(name)
    }
    return null
}