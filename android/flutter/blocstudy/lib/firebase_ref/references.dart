import 'package:cloud_firestore/cloud_firestore.dart';

final fireStore = FirebaseFirestore.instance;
final questionPaperRF = fireStore.collection('questionPaper');

/// sub collection 을 생성해서 firestore 에 등록하는 방법을 보여주는 것임
DocumentReference questionRF({
  required String paperId,
  required String questionId,
}) => questionPaperRF.doc(paperId).collection("questions").doc(questionId);