import 'dart:convert';

import 'package:blocstudy/models/question_paper_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';

/// 앱이 실행하는동안 한번만 호출되는 것임
class DataUploader extends GetxController {
  @override
  void onReady() {
    uploadData();
    super.onReady();
  }

  Future<void> uploadData() async {
    /// 내부 저장소에 저장한 데이터를 불러와서 확인 하기 위해 사용 하는 부분
    final manifestContent = await DefaultAssetBundle.of(Get.context!)
        .loadString("AssetManifest.json");

    final Map<String, dynamic> manifestMap = json.decode(manifestContent);

    final papersInAssets = manifestMap.keys
        .where((path) =>
            path.startsWith("assets/DB/paper") && path.contains(".json"))
        .toList();

    List<QuestionPaperModel> questionPapers = [];

    for(var paper in papersInAssets){
      String stringPaperContent = await rootBundle.loadString(paper);

      questionPapers.add(QuestionPaperModel.fromJson(json.decode(stringPaperContent)));
      print("items Numbers ${questionPapers[0].id}");
    }
  }
}
