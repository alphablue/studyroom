import 'package:blocstudy/configs/themes/app_colors.dart';
import 'package:blocstudy/widgets/app_circle_button.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class AppIntroductionScreen extends StatelessWidget {
  const AppIntroductionScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: BoxDecoration(gradient: mainGradient(context)),
        alignment: Alignment.center,
        child: Padding(
          padding: EdgeInsets.symmetric(horizontal: Get.width * 0.2),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Icon(
                Icons.star,
                size: 65,
              ),
              SizedBox(height: 40,),
              const Text(
                  'This is a study app. You can use it as you want.'
                  'If you understand how it works you would be able to scale it.'
                  'With this you will master firebase backend and flutter front end.',
              style: TextStyle(
                fontSize: 18,
                color: onSurfaceTextColor,
              ),
              textAlign: TextAlign.center,
              ),
              SizedBox(height: 40,),
              AppCircleButton(
                onTap: () => null,
                child: const Icon(
                  Icons.arrow_forward,
                  size: 48,
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
