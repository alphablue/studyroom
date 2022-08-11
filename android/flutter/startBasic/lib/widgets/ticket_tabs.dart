import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:startbasic/utils/app_layout.dart';

class AppTicketTabs extends StatelessWidget {
  final String firstTab;
  final String secondTab;

  const AppTicketTabs({Key? key, required this.firstTab, required this.secondTab}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final size = AppLayout.getSize(context);

    return FittedBox(
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
                child: Text(firstTab),
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
                child: Text(secondTab),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
