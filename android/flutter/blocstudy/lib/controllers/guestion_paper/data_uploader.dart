import 'package:get/get.dart';

/// 앱이 실행하는동안 한번만 호출되는 것임
class DataUploader extends GetxController {
  @override
  void onReady() {
    uploadData();
    super.onReady();
  }

  void uploadData() {
    print("Data is uploading");
  }
}