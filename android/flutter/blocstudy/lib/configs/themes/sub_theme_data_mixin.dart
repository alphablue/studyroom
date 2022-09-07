import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

import 'app_colors.dart';

/// dart 에서는 상속할 수 있는 클래스는 하나이기 때문에 여러 상속을 받아 활용 하기위해서는
/// mixin 을 활용해야 한다.
mixin SubThemeData {
  TextTheme getTextThemes() {
    return GoogleFonts.quicksandTextTheme(
        const TextTheme(bodyText1: TextStyle(
          fontWeight: FontWeight.w400,
        ),
            bodyText2: TextStyle(fontWeight: FontWeight.w400)
        )
    );
  }

  IconThemeData getIconTheme() {
    return const IconThemeData(color: onSurfaceTextColor, size: 16);
  }
}