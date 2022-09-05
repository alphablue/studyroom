class Answer {
  final String? identifier;
  final String? answer;

  Answer({this.identifier, this.answer});

  /// json 파일에 맞는 모델을 만들기 위한 작업, 초기화를 직접 하기 위해서는
  /// : 를 사용해서 작업 할 수 있다.
  Answer.fromJson(Map<String, dynamic> json)
  : identifier = json['identifier'] as String?,
  answer = json['Answer'] as String?;
}
