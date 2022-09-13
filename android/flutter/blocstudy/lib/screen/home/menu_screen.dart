import 'package:blocstudy/configs/themes/app_colors.dart';
import 'package:blocstudy/configs/themes/ui_parameters.dart';
import 'package:blocstudy/controllers/zoom_drawaer_controller.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class MyMenuScreen extends GetView<MyZoomDrawerController> {
  const MyMenuScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: UIParameters.mobileScreenPadding,
      decoration: BoxDecoration(gradient: mainGradient()),
      child: Theme(
        data: ThemeData(textButtonTheme:
        TextButtonThemeData(
            style: TextButton.styleFrom(primary: onSurfaceTextColor)
        )),
        child: SafeArea(
          child: Stack(
            children: [
              Positioned(
                top: 0,
                  right: 15,
                  child: BackButton(
                color: Colors.white,
                onPressed: () {
                  controller.toogleDrawer();
                },
              ))
            ],
          ),
        ),
      ),
    );
  }
}
