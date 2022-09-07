import 'dart:convert';

import 'package:blocstudy/firebase_ref/references.dart';
import 'package:blocstudy/models/question_paper_model.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_core/firebase_core.dart';
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
    WidgetsFlutterBinding.ensureInitialized();
    await Firebase.initializeApp();

    final fireStore = FirebaseFirestore.instance;
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

    var batch = fireStore.batch();

    for( var paper in questionPapers) {
      batch.set(questionPaperRF.doc(paper.id), {
        "title":paper.title,
        "image_url":paper.imageUrl,
        "description":paper.description,
        "time_seconds":paper.timeSeconds,
        "questions_count":paper.questions == null ?0:paper.questions!.length,
      });

      for(var questions in paper.questions!) {
        final questionPath = questionRF(paperId: paper.id, questionId: questions.id);
        batch.set(questionPath, {
          "question":questions.question,
          "correct_answer":questions.correctAnswer
        });
        
        for (var answer in questions.answers) {
          batch.set(questionPath.collection("answers").doc(answer.identifier), {
            "identifier": answer.identifier,
            "answer": answer.answer
          });
        }
      }
    }

    /// firebase 에 데이터를 게시하기 위한 부분 batch 에 데이터가 담겨 있는 상태이다.
    await batch.commit();
  }
}
