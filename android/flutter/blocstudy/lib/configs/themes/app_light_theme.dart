
import 'package:blocstudy/configs/themes/sub_theme_data_mixin.dart';
import 'package:flutter/material.dart';

const Color primaryLightColorLight = Color(0xFF3ac3cb);
const Color primaryColorLight = Color(0xFFf85187);
const Color mainTextColorLight = Color.fromARGB(250, 40, 40, 40);
const Color cardColor = Color.fromARGB(255, 254, 254, 255);

/// with 는 mixin 을 사용하기 위한 것
/// 라이트 테마를 선택 했을 때 전체적인 화면의 설정을 변경하기 위해서 각 요소들에 영향을 주는
/// 부분을 copyWith 으로 변경 해 주는 것
class LightTheme with SubThemeData {
  buildLightTheme() {
    final ThemeData systemLightTheme = ThemeData.light();
    return systemLightTheme.copyWith(
      primaryColor: primaryColorLight,
      iconTheme: getIconTheme(),
      cardColor: cardColor,
      textTheme: getTextThemes().apply(
        bodyColor: mainTextColorLight,
        displayColor: mainTextColorLight
      )
    );
  }
}
