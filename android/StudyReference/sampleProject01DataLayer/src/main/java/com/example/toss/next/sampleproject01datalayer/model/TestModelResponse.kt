package com.example.toss.next.sampleproject01datalayer.model

class TestModelResponse(val name: String)

// 아래처럼 한번더 거치는 경우는 서버에서 모든 데이터가 안 왔을 수도 있기 때문에 안정성을 위한 과정이다.
// 항상 서버를 믿는 것이 아니라 서버가 틀리게 올 수 있음을 주의해서 코드를 작성하기(에러를 앱단에서 제거)
fun TestModelResponse.toDomainModel(): TestModel {
        return TestModel(name)
}