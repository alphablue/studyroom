import 'package:blocstudy/controllers/auth_controller.dart';
import 'package:blocstudy/firebase_ref/references.dart';
import 'package:blocstudy/models/question_paper_model.dart';
import 'package:blocstudy/services/firebase_storage_service.dart';
import 'package:blocstudy/util/AppLogger.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';

class QuestionPaperController extends GetxController {

  final allPapersImages = <String>[].obs;
  final allPapers = <QuestionPaperModel>[].obs;

  @override
  void onReady() {
    getAllPapers();
    super.onReady();
  }

  Future<void> getAllPapers() async {
    List<String> imgName = ["biology", "chemistry", "maths", "physics"];

    try {
      await Firebase.initializeApp();

      QuerySnapshot<Map<String, dynamic>> data = await questionPaperRF.get();

      final paperList = data.docs
          .map((paper) => QuestionPaperModel.fromSnapshot(paper))
          .toList();
      allPapers.assignAll(paperList);

      for (var paper in paperList) {
        final imgUrl = await Get.find<FirebaseStorageService>().getImage(paper.title);
        paper.imageUrl = imgUrl;
      }

      allPapers.assignAll(paperList);
    } catch (e) {
      AppLogger.e(e);
    }
  }

  void navigateToQuestions({ required QuestionPaperModel paperModel, bool tryAgain = false}) {
    AuthController _authController = Get.find();
    if (_authController.isLoggedIn()) {
      if (tryAgain) {
        Get.back();
        // Get.offNamed()
      }else {
        // Get.toNamed()
      }
    }else {
      print("The title is ${paperModel.title}");
      _authController.showLoginAlertDialogue();
    }
  }
}
