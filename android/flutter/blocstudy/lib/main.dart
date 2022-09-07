import 'package:blocstudy/controllers/data_uploader_screen.dart';
import 'package:blocstudy/firebase_options.dart';
import 'package:blocstudy/screen/introduction/introduction.dart';
import 'package:blocstudy/screen/splash/splash_screen.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const GetMaterialApp(
      home: AppIntroductionScreen(),
    );
  }
}

/*Future<void> main() async {
  // WidgetsFlutterBinding.ensureInitialized();
  // await Firebase.initializeApp(options: DefaultFirebaseOptions.currentPlatform);
  runApp(GetMaterialApp(home: DataUploaderScreen()));
}*/
