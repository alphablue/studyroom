import 'package:blocstudy/configs/themes/app_dark_theme.dart';
import 'package:blocstudy/configs/themes/app_light_theme.dart';
import 'package:blocstudy/configs/themes/ui_parameters.dart';
import 'package:flutter/material.dart';

const Color onSurfaceTextColor = Colors.white;

/// 우리앱은 그레디언트 색상을 배경으로 뿌려줄 것이기 때문에 시작점과 끝점을 지정해 줘야한다.
const mainGradientLight = LinearGradient(
    begin: Alignment.topLeft,
    end: Alignment.bottomRight,
    colors: [primaryLightColorLight, primaryColorLight]);

const mainGradientDark = LinearGradient(
    begin: Alignment.topLeft,
    end: Alignment.bottomRight,
    colors: [primaryDarkColorDark, primaryColorDark]);

LinearGradient mainGradient() =>
    UIParameters.isDarkMode() ? mainGradientDark : mainGradientLight;

Color customScaffoldColor(BuildContext context) =>
    UIParameters.isDarkMode()
        ? const Color(0xff2e3c62)
        : const Color.fromARGB(255, 240, 237, 255);