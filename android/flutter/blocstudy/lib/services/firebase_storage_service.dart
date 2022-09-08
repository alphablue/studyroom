import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';

import '../firebase_ref/references.dart';


class FirebaseStorageService extends GetxService {
  Future<String?> getImage(String? imgName) async {

    if (imgName == null) {
      return null;
    }

    try {
      await Firebase.initializeApp();

      /// 데이터를 얻으려 하는 경로를 말함
      var urlRef = firebaseStorage
          .child("question_paper_images")
          .child("${imgName.toLowerCase()}.png");

      var imgUrl = await urlRef.getDownloadURL();

      return imgUrl;
    } catch (e) {
      print(e);
      return null;
    }
  }
}
