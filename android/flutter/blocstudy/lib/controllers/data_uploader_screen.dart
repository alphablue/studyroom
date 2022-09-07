import 'package:blocstudy/controllers/guestion_paper/data_uploader.dart';
import 'package:blocstudy/firebase_ref/loading_status.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class DataUploaderScreen extends StatelessWidget {
  DataUploaderScreen({Key? key}) : super(key: key);

  /// dataUploader를 주입 해서 싱글턴 객체를 사용 하는 것
  DataUploader controller = Get.put(DataUploader());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Obx(() => Text(
            controller.loadingStatus.value == LoadingStatus.completed
                ? "Uploading completed"
                : "Uploading ... ")),
      ),
    );
  }
}
