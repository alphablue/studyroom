import 'package:blocstudy/configs/themes/ui_parameters.dart';
import 'package:blocstudy/controllers/guestion_paper/question_paper_controller.dart';
import 'package:blocstudy/screen/home/question_card.dart';
import 'package:blocstudy/widgets/content_area.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    QuestionPaperController _questionPaperController = Get.find();

    return Scaffold(
      body: ContentArea(
        addPadding: false,
        child: Obx(() => ListView.separated(
          padding: UIParameters.mobileScreenPadding,
          shrinkWrap: true,
            itemBuilder: (BuildContext context, int index) {
              return QuestionCard(model: _questionPaperController.allPapers[index]);
            },
            separatorBuilder: (BuildContext context, int index) {
              return SizedBox(
                height: 20,
              );
            },
            itemCount: _questionPaperController.allPapers.length),
    ),
      ));
  }
}
