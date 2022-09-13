import 'package:blocstudy/configs/themes/custom_text_styles.dart';
import 'package:blocstudy/configs/themes/ui_parameters.dart';
import 'package:blocstudy/models/question_paper_model.dart';
import 'package:blocstudy/widgets/app_icon_text.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class QuestionCard extends StatelessWidget {
  const QuestionCard({Key? key, required this.model}) : super(key: key);

  final QuestionPaperModel model;

  @override
  Widget build(BuildContext context) {
    const double _padding = 10.0;

    return Container(
      decoration: BoxDecoration(
          borderRadius: UIParameters.cardBorderRadius,
          color: Theme.of(context).cardColor),
      child: Padding(
        padding: const EdgeInsets.all(_padding),
        child: Stack(
          clipBehavior: Clip.none,
          children: [
            Row(
              children: [
                ClipRRect(
                  borderRadius: BorderRadius.circular(10),
                  child: ColoredBox(
                    color: Theme.of(context).primaryColor.withOpacity(0.2),
                    child: SizedBox(
                      width: Get.width * 0.15,
                      height: Get.width * 0.15,
                      child: CachedNetworkImage(
                        imageUrl: model.imageUrl!,
                        placeholder: (context, url) => Container(
                          alignment: Alignment.center,
                          child: const CircularProgressIndicator(),
                        ),
                        errorWidget: (context, url, error) =>
                            Image.asset("assets/images/app_splash_logo.png"),
                      ),
                    ),
                  ),
                ),
                const SizedBox(
                  width: 12,
                ),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        model.title,
                        style: cartTitles(context),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(top: 10, bottom: 15),
                        child: Text(model.description),
                      ),
                      Row(
                        children: [
                          AppIconText(
                              icon: Icon(
                                Icons.help_outline_sharp,
                                color: UIParameters.isDarkMode()
                                    ? Colors.white
                                    : Theme.of(context)
                                        .primaryColor
                                        .withOpacity(0.6),
                              ),
                              text: Text(
                                "${model.questionCount} questions",
                                style: detailText.copyWith(
                                  color: UIParameters.isDarkMode()
                                      ? Colors.white
                                      : Theme.of(context)
                                          .primaryColor
                                          .withOpacity(0.6),
                                ),
                              )),
                          AppIconText(
                              icon: Icon(
                                Icons.timer,
                                color: UIParameters.isDarkMode()
                                    ? Colors.white
                                    : Theme.of(context)
                                        .primaryColor
                                        .withOpacity(0.6),
                              ),
                              text: Text(
                                "${model.timeInMinits()}",
                                style: detailText.copyWith(
                                  color: UIParameters.isDarkMode()
                                      ? Colors.white
                                      : Theme.of(context)
                                          .primaryColor
                                          .withOpacity(0.6),
                                ),
                              ))
                        ],
                      )
                    ],
                  ),
                )
              ],
            ),
            Positioned(
              bottom: -_padding,
                right: -_padding,
                child: GestureDetector(
                  child: Container(
                    padding: const EdgeInsets.symmetric(
                        vertical: 10, horizontal: 16),
                    decoration:
                        BoxDecoration(
                            color: Theme.of(context).primaryColor,
                        borderRadius: BorderRadius.only(
                          topLeft: Radius.circular(cardBorderRadius),
                          bottomRight: Radius.circular(cardBorderRadius)
                        )),
                    child: const Icon(Icons.wine_bar,),
                  ),
                ))
          ],
        ),
      ),
    );
  }
}
