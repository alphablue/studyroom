import 'package:blocstudy/services/firebase_storage_service.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';

class QuestionPaperController extends GetxController {

  final allPapersImages = <String>[].obs;

  @override
  void onReady() {
    getAllPapers();
    super.onReady();
  }

  Future<void> getAllPapers() async {
    List<String> imgName = [
      "biology",
      "chemistry",
      "maths",
      "physics"
    ];

    try {
      for(var img in imgName) {
        final imgUrl = await Get.find<FirebaseStorageService>().getImage(img);
        allPapersImages.add(imgUrl!);
      }
    }catch(e) {
      print(e);
    }
  }
}