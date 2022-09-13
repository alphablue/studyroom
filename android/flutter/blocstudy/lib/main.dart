import 'package:blocstudy/binding/initial_bindings.dart';
import 'package:blocstudy/configs/themes/app_light_theme.dart';
import 'package:blocstudy/controllers/theme_controller.dart';
import 'package:blocstudy/routes/app_routes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import 'configs/themes/app_dark_theme.dart';

void main() {

  /// 앱이 시작될때 주입 받도록 한 것
  WidgetsFlutterBinding.ensureInitialized();
  InitialBindings().dependencies();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      debugShowCheckedModeBanner: false,
      theme: Get.find<ThemeController>().lightTheme,
      getPages: AppRoutes.routes(),
    );
  }
}

/*Future<void> main() async {
  // WidgetsFlutterBinding.ensureInitialized();
  // await Firebase.initializeApp(options: DefaultFirebaseOptions.currentPlatform);
  runApp(GetMaterialApp(home: DataUploaderScreen()));
}*/
