import 'package:get/get.dart';

class AuthController extends GetxController {

  @override
  void onReady() {
    initAuth();
    super.onReady();
  }
  
  void initAuth() async{
    await Future.delayed(const Duration(seconds: 2));
    navigateToIntroduction();
  }
  
  void navigateToIntroduction() {
    /// 네비게이션의 루트를 변경해 준다. 기존에 있던 루트를 제거하고 새로운 루트가 됨
    Get.offAllNamed("/introduction");
  }
}