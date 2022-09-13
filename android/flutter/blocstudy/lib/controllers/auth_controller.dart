import 'package:blocstudy/firebase_ref/references.dart';
import 'package:blocstudy/screen/login/login_screen.dart';
import 'package:blocstudy/util/AppLogger.dart';
import 'package:blocstudy/widgets/dialogs/dialogue_widget.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';
import 'package:google_sign_in/google_sign_in.dart';

class AuthController extends GetxController {

  @override
  void onReady() {
    initAuth();
    super.onReady();
  }

  late FirebaseAuth _auth;
  final _user = Rxn<User>();
  late Stream<User?> _authStateChanges;

  void initAuth() async{
    await Future.delayed(const Duration(seconds: 2));
    await Firebase.initializeApp();

    /// 파이어 베이스 인증을 위한 부분
    _auth = FirebaseAuth.instance;
    _authStateChanges = _auth.authStateChanges();
    _authStateChanges.listen((User? user) {
      _user.value = user;
    });
    navigateToIntroduction();
  }

  signInWithGoogle() async {
    final GoogleSignIn _googleSignIn = GoogleSignIn();
    try {
      GoogleSignInAccount? account = await _googleSignIn.signIn();
      if(account != null) {
        /// 구글에서 주는 유일한 id 임
        final _authAccount = await account.authentication;
        final _credential = GoogleAuthProvider.credential(
          idToken: _authAccount.idToken,
          accessToken: _authAccount.accessToken
        );

        await _auth.signInWithCredential(_credential);
        await saveUser(account);
      }

    }on Exception catch(error) {
      AppLogger.e(error);
    }
  }

  saveUser(GoogleSignInAccount account) {
    userRf.doc(account.email).set({
      "email": account.email,
      "name": account.displayName,
      "profilepic": account.photoUrl
    });
  }

  void navigateToIntroduction() {
    /// 네비게이션의 루트를 변경해 준다. 기존에 있던 루트를 제거하고 새로운 루트가 됨
    Get.offAllNamed("/introduction");
  }

  void showLoginAlertDialogue() {
    Get.dialog(Dialogs.questionStartDialogue(onTap: () {
      Get.back();
      /// 로그인 페이지로 이동이 필요함
      navigationToLoginPage();
    }),
    barrierDismissible: false);
  }

  void navigationToLoginPage() {
    Get.toNamed(LoginScreen.routeName);
  }

  bool isLoggedIn() {
    /// 로그인 상태 확인
    return _auth.currentUser != null;
  }
}