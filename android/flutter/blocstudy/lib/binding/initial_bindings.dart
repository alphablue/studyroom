import 'package:blocstudy/controllers/auth_controller.dart';
import 'package:blocstudy/controllers/theme_controller.dart';
import 'package:get/get.dart';

import '../services/firebase_storage_service.dart';

/// DI 활용을 위해서 만드는 클래스로 controller 를 설정해 줘야 한다.
///
class InitialBindings implements Bindings {

  @override
  void dependencies() {

    /// 주입을 받을 수 있도록 하고 메모리에 상태를 유지하도록 함
    Get.put(AuthController(), permanent: true);
    Get.put(ThemeController());
    Get.put(FirebaseStorageService());
  }
}