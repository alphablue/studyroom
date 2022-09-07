import 'package:get/get.dart';
import '../screen/introduction/introduction.dart';

/// 화면 이동을 위한 route 와 navigation 적용을 위한 사전 작업
class AppRoutes{
  static List<GetPage> routes () => [
    GetPage(name: "/", page: () => AppIntroductionScreen()),
  ];
}