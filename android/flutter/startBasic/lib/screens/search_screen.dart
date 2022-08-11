import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:gap/gap.dart';
import 'package:startbasic/utils/app_layout.dart';
import 'package:startbasic/utils/app_styles.dart';
import 'package:startbasic/widgets/icon_text_widget.dart';

import '../widgets/doutble_text_widget.dart';

class SearchScreen extends StatelessWidget {
  const SearchScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final size = AppLayout.getSize(context);
    return Scaffold(
      backgroundColor: Styles.bgColor,
      body: ListView(
        padding: EdgeInsets.symmetric(
            horizontal: AppLayout.getWidth(20),
            vertical: AppLayout.getHeight(20)),
        children: [
          Gap(AppLayout.getHeight(40)),
          Text(
            "What are\n you looking for?",
            style: Styles.headLineStyle1.copyWith(
                fontSize: AppLayout.getWidth(
                    35)), //fontSize: 35 를 width 사이즈로 조정이 가능하다
          ),
          Gap(AppLayout.getHeight(20)),
          FittedBox(
            child: Container(
              padding: const EdgeInsets.all(3.5),
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(AppLayout.getHeight(50)),
                  color: const Color(0xFFf4f6fd)),
              child: Row(
                children: [
                  /**
                   * 비행기 티켓
                   * */
                  Container(
                    width: size.width * 0.44,
                    padding:
                        EdgeInsets.symmetric(vertical: AppLayout.getHeight(7)),
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.horizontal(
                            //horizontal 을 넣으면 좌, 우 중 하나에게 radius 값을 줄 수 있음
                            left: Radius.circular(AppLayout.getHeight(50))),
                        color: Colors.white),
                    child: Center(
                      child: Text("Airline Tickets"),
                    ),
                  ),

                  /**
                   * 호텔
                   * */
                  Container(
                    width: size.width * 0.44,
                    padding:
                        EdgeInsets.symmetric(vertical: AppLayout.getHeight(7)),
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.horizontal(
                            //horizontal 을 넣으면 좌, 우 중 하나에게 radius 값을 줄 수 있음
                            right: Radius.circular(AppLayout.getHeight(50))),
                        /**
                         * 상위 컨테이너의 색을 그대로 사용하기 위해서 transparent 를 사용함
                         * */
                        color: Colors.transparent),
                    child: Center(
                      child: Text("Hotel"),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Gap(AppLayout.getHeight(25)),
          AppIconText(icon: Icons.flight_takeoff_rounded, text: "Departure"),
          Gap(AppLayout.getHeight(15)),
          AppIconText(icon: Icons.flight_land_rounded, text: "Arrival"),
          Gap(AppLayout.getHeight(25)),
          Container(
              padding: EdgeInsets.symmetric(
                  vertical: AppLayout.getWidth(18),
                  horizontal: AppLayout.getWidth(15)),
              decoration: BoxDecoration(
                color: Color(0xd91130ce),
                borderRadius: BorderRadius.circular(AppLayout.getWidth(10)),
              ),
              child: Center(
                child: Text(
                  "find tickets",
                  style: Styles.textStyle.copyWith(color: Colors.white),
                ),
              )),
          Gap(AppLayout.getHeight(40)),
          const AppDoubleTextWidget(
              bigText: 'Upcoming Flights', smallText: 'View all'),
          Gap(AppLayout.getHeight(15)),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Container(
                height: AppLayout.getHeight(425),
                width: size.width * 0.42,
                padding: EdgeInsets.symmetric(
                    horizontal: AppLayout.getHeight(15),
                    vertical: AppLayout.getWidth(15)),
                decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius:
                        BorderRadius.circular(AppLayout.getHeight(20)),
                    boxShadow: [
                      BoxShadow(
                          color: Colors.grey.shade200,
                          blurRadius: 1,
                          spreadRadius: 1)
                    ]),
                child: Column(
                  children: [
                    Container(
                      height: AppLayout.getHeight(190),
                      decoration: BoxDecoration(
                          borderRadius:
                              BorderRadius.circular(AppLayout.getHeight(12)),
                          image: const DecorationImage(
                              fit: BoxFit.cover,
                              image: AssetImage("assets/images/sit.jpg"))),
                    ),
                    Gap(AppLayout.getHeight(12)),
                    Text(
                      "20% discount on the early booking of this flight, Don't miss out this chance",
                      style: Styles.headLineStyle2,
                    )
                  ],
                ),
              ),
              Column(
                children: [
                  /**
                   * 플러터에서 스택은 다양한 UI를 만들기위해서 사용되는 방법임 position 조정도 가능하고
                   * 자료를 찾아볼 필요가 있음
                   * */
                  Stack(
                    children: [
                      Container(
                        width: size.width * 0.44,
                        height: AppLayout.getHeight(200),
                        decoration: BoxDecoration(
                            color: Color(0xff3ab8b8),
                            borderRadius:
                                BorderRadius.circular(AppLayout.getHeight(18))),
                        padding: EdgeInsets.symmetric(
                            vertical: AppLayout.getHeight(15),
                            horizontal: AppLayout.getHeight(15)),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text("Discount\nfor survey",
                                style: Styles.headLineStyle2.copyWith(
                                    fontWeight: FontWeight.bold,
                                    color: Colors.white)),
                            Gap(AppLayout.getHeight(10)),
                            Text(
                                "Take the survey about our services and get discount",
                                style: Styles.headLineStyle2.copyWith(
                                    fontWeight: FontWeight.w500,
                                    color: Colors.white,
                                    fontSize: 18)),
                          ],
                        ),
                      ),
                      Positioned(
                        right: -45,
                        top: -40,
                        child: Container(
                          padding: EdgeInsets.all(AppLayout.getHeight(30)),
                          decoration: BoxDecoration(
                              shape: BoxShape.circle,
                              border: Border.all(
                                  width: 18, color: Color(0xff189999)),
                              color: Colors.transparent),
                        ),
                      ),
                    ],
                  ),
                  Gap(AppLayout.getHeight(15)),
                  Container(
                    width: size.width * 0.44,
                    height: AppLayout.getHeight(210),
                    padding: EdgeInsets.symmetric(
                        vertical: AppLayout.getHeight(15),
                        horizontal: AppLayout.getHeight(15)),
                    decoration: BoxDecoration(
                        borderRadius:
                            BorderRadius.circular(AppLayout.getHeight(18)),
                        color: const Color(0xffec6545)),
                    /**
                     * 이모티콘 넣기
                     */
                    child: Column(
                      children: [
                        Text(
                          'Take love',
                          style: Styles.headLineStyle2.copyWith(
                              color: Colors.white, fontWeight: FontWeight.bold),
                          textAlign: TextAlign.center,
                        ),
                        Gap(AppLayout.getHeight(5)),
                        RichText(text: const TextSpan(
                          children: [
                            TextSpan(
                              text: '😍',
                              style: TextStyle(fontSize: 38)
                            ),
                            TextSpan(
                                text: '🥰',
                                style: TextStyle(fontSize: 38)
                            ),
                            TextSpan(
                                text: '😘',
                                style: TextStyle(fontSize: 38)
                            ),
                          ]
                        ))
                      ],
                    ),
                  )
                ],
              )
            ],
          )
        ],
      ),
    );
  }
}
