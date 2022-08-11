import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:gap/gap.dart';
import 'package:startbasic/utils/app_layout.dart';
import 'package:startbasic/utils/app_styles.dart';
import 'package:startbasic/widgets/icon_text_widget.dart';

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
            vertical: AppLayout.getHeight(20)
        ),
        children: [
          Gap(AppLayout.getHeight(40)),
          Text(
            "What are\n you looking for?",
            style: Styles.headLineStyle1.copyWith(fontSize: AppLayout.getWidth(35)), //fontSize: 35 를 width 사이즈로 조정이 가능하다
          ),
          Gap(AppLayout.getHeight(20)),
          FittedBox(
            child: Container(
              padding: const EdgeInsets.all(3.5),
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(AppLayout.getHeight(50)),
                color: const Color(0xFFf4f6fd)
              ),
              child: Row(
                children: [
                  /**
                   * 비행기 티켓
                   * */
                  Container(
                    width: size.width * 0.44,
                    padding: EdgeInsets.symmetric(vertical: AppLayout.getHeight(7)),
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.horizontal( //horizontal 을 넣으면 좌, 우 중 하나에게 radius 값을 줄 수 있음
                            left: Radius.circular(AppLayout.getHeight(50))
                        ),
                        color: Colors.white
                    ),
                    child: Center(child: Text("Airline Tickets"),),
                  ),

                  /**
                   * 호텔
                   * */
                  Container(
                    width: size.width * 0.44,
                    padding: EdgeInsets.symmetric(vertical: AppLayout.getHeight(7)),
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.horizontal( //horizontal 을 넣으면 좌, 우 중 하나에게 radius 값을 줄 수 있음
                            right: Radius.circular(AppLayout.getHeight(50))
                        ),
                        /**
                         * 상위 컨테이너의 색을 그대로 사용하기 위해서 transparent 를 사용함
                         * */
                        color: Colors.transparent
                    ),
                    child: Center(child: Text("Hotel"),),
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
            padding: EdgeInsets.symmetric(vertical: AppLayout.getWidth(18), horizontal: AppLayout.getWidth(15)),
            decoration: BoxDecoration(
              color: Color(0xd91130ce),
              borderRadius: BorderRadius.circular(AppLayout.getWidth(10)),
            ),
            child: Center(
              child: Text(
                "find tickets",
                style: Styles.textStyle.copyWith(color: Colors.white),
              ),
            )
          )
        ],
      ),
    );
  }
}
