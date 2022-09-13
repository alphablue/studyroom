import 'package:blocstudy/configs/themes/app_colors.dart';
import 'package:blocstudy/configs/themes/custom_text_styles.dart';
import 'package:blocstudy/configs/themes/ui_parameters.dart';
import 'package:blocstudy/controllers/guestion_paper/question_paper_controller.dart';
import 'package:blocstudy/controllers/zoom_drawaer_controller.dart';
import 'package:blocstudy/screen/home/menu_screen.dart';
import 'package:blocstudy/screen/home/question_card.dart';
import 'package:blocstudy/widgets/app_icons.dart';
import 'package:blocstudy/widgets/content_area.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_zoom_drawer/config.dart';
import 'package:flutter_zoom_drawer/flutter_zoom_drawer.dart';
import 'package:get/get.dart';

class HomeScreen extends GetView<MyZoomDrawerController> {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    QuestionPaperController _questionPaperController = Get.find();

    return Scaffold(
      body: GetBuilder<MyZoomDrawerController>(builder: (_) {
        return ZoomDrawer(
          borderRadius: 50.0,
          controller: _.zoomDrawerController,
          showShadow: false,
          angle: 0.0,
          style: DrawerStyle.defaultStyle,
          menuScreenWidth: double.maxFinite,
          slideWidth: MediaQuery.of(context).size.width * 0.75,
          menuScreen: const MyMenuScreen(),
          mainScreen: Container(
            decoration: BoxDecoration(gradient: mainGradient()),
            child: SafeArea(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Padding(
                    padding: EdgeInsets.all(mobileScreenPadding),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        IconButton(
                          icon: const Icon(AppIcons.menuLeft),
                          onPressed: controller.toogleDrawer,
                          iconSize: 16,
                          padding: EdgeInsets.zero,
                        ),
                        const SizedBox(height: 10,),
                        Padding(
                          padding: const EdgeInsets.symmetric(vertical: 10),
                          child: Row(
                            children: [
                              const Icon(AppIcons.peace),
                              Text("Hello friend",
                                style: detailText.copyWith(
                                    color: onSurfaceTextColor
                                ),)
                            ],
                          ),
                        ),
                        Text(
                          "What do you want to learn today?",
                          style: headerText,
                        )
                      ],
                    ),
                  ),
                  Expanded(
                    child: Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 8),
                      child: ContentArea(
                        addPadding: false,
                        child: Obx(
                              () => ListView.separated(
                              padding: UIParameters.mobileScreenPadding,
                              shrinkWrap: true,
                              itemBuilder: (BuildContext context, int index) {
                                return QuestionCard(
                                    model: _questionPaperController.allPapers[index]);
                              },
                              separatorBuilder: (BuildContext context, int index) {
                                return SizedBox(
                                  height: 20,
                                );
                              },
                              itemCount: _questionPaperController.allPapers.length),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        );
      },),
    );
  }
}
