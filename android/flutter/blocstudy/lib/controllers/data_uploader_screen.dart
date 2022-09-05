import 'package:blocstudy/controllers/guestion_paper/data_uploader.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class DataUploaderScreen extends StatelessWidget {
  DataUploaderScreen({Key? key}) : super(key: key);

  /// dataUploader를 주입 해서 싱글턴 객체를 사용 하는 것
  DataUploader controller = Get.put(DataUploader());

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text("uploading"),
      ),
    );
  }
}
