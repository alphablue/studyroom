
import 'package:flutter/cupertino.dart';

/// 싱글턴 객채를 만들어 사용 할 것
class AppIcons {
  AppIcons._();

  static const fontFam = 'AppIcons';
  static const IconData trophyOutLine = IconData(0xe808, fontFamily: fontFam);
  static const IconData menuLeft = IconData(0xe805, fontFamily: fontFam);
  static const IconData peace = IconData(0xe806, fontFamily: fontFam);
}