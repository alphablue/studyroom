import 'package:flutter/material.dart';
import 'package:get/get.dart';

class AppLayout {
  static getSize(BuildContext context) {
    return MediaQuery.of(context).size;
  }

  static getScreenHeight() {
    return Get.height;
  }

  static getScreenWidth() {
    return Get.width;
  }

  /// 화면의 크기에 따라서 비율이 달라지기 때문에
  /// 그 비율에 맞는 픽셀을 가져오도록 함함
 ///
  static getHeight(double pixels) {
    double x = getScreenHeight() / pixels; // 844/200 => 4.22
    return getScreenHeight() / x;
  }

  static getWidth(double pixels) {
    double x = getScreenWidth() / pixels;
    return getScreenWidth() / x;
  }
}