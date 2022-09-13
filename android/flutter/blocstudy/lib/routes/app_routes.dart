import 'package:blocstudy/controllers/guestion_paper/question_paper_controller.dart';
import 'package:blocstudy/controllers/zoom_drawaer_controller.dart';
import 'package:blocstudy/screen/home/home_screen.dart';
import 'package:blocstudy/screen/login/login_screen.dart';
import 'package:blocstudy/screen/splash/splash_screen.dart';
import 'package:get/get.dart';
import '../screen/introduction/introduction.dart';

/// 화면 이동을 위한 route 와 navigation 적용을 위한 사전 작업
class AppRoutes {
  static List<GetPage> routes() => [
        GetPage(name: "/", page: () => SplashScreen()),
        GetPage(name: "/introduction", page: () => AppIntroductionScreen()),
        GetPage(
            name: "/home",
            page: () => const HomeScreen(),
            binding: BindingsBuilder(() {
              Get.put(QuestionPaperController());
              Get.put(MyZoomDrawerController());
            })),
        GetPage(name: LoginScreen.routeName, page: () => LoginScreen())
      ];
}
