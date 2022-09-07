import 'package:flutter/material.dart';

class UIParameters {
  /// 다크모드와 라이트 모드를 구분해주는 것
  static bool isDarkMode(BuildContext context) {
    return Theme.of(context).brightness == Brightness.dark;
  }
}