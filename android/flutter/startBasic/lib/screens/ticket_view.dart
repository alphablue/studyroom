import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:gap/gap.dart';
import 'package:get/get.dart';
import 'package:startbasic/utils/app_layout.dart';
import 'package:startbasic/utils/app_styles.dart';
import 'package:startbasic/widgets/column_layout.dart';
import 'package:startbasic/widgets/layout_builder_widget.dart';
import 'package:startbasic/widgets/ticket_container.dart';

class TicketView extends StatelessWidget {
  final Map<String, dynamic> ticket;
  final bool? isColor;

  const TicketView({Key? key, required this.ticket, this.isColor})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    final size = AppLayout.getSize(context);

    return SizedBox(
      /**
       * 티켓이 화면 전체를 채우는 것 보다 일부분은 덜채워서 여러개가 있다는 것을 보여 주는
       * 것이 더 직관적이다.
       * */
      width: size.width * 0.85,
      height: AppLayout.getHeight(GetPlatform.isAndroid == true ? 156 : 158),
      child: Container(
        margin: EdgeInsets.only(right: AppLayout.getHeight(16)),
        child: Column(
          children: [
            /**
             * 티켓의 파란부분을 보여주는 뷰
             * */
            Container(
              decoration: BoxDecoration(
                color: isColor == null ? const Color(0xFF526799) : Colors.white,
                borderRadius: BorderRadius.only(
                    topLeft: Radius.circular(AppLayout.getHeight(21)),
                    topRight: Radius.circular(AppLayout.getHeight(21))),
              ),
              padding: EdgeInsets.all(AppLayout.getHeight(16)),
              child: Column(
                children: [
                  Row(
                    children: [
                      Text(
                        ticket['from']['code'],
                        style: isColor == null
                            ? Styles.headLineStyle3
                                .copyWith(color: Colors.white)
                            : Styles.headLineStyle3,
                      ),
                      const Spacer(),
                      const TicketContainer(
                        isColor: true,
                      ),
                      Expanded(
                          child: Stack(children: [
                        SizedBox(
                          /**
                               *  "-" 가 화면의 크기에 따라서 동적으로 보여주기 위해서 화면의 크기를
                               *  알아야 한다. 그것을 위해서 LayoutBuilder 로 감싸준 것
                               * */
                          height: 24,
                          child: LayoutBuilder(
                            builder: (BuildContext context,
                                BoxConstraints constraints) {
                              print(
                                  "The width is ${constraints.constrainWidth()}");
                              return Flex(
                                direction: Axis.horizontal,
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                mainAxisSize: MainAxisSize.max,
                                children: List.generate(
                                    (constraints.constrainWidth() / 6).floor(),
                                    (index) => SizedBox(
                                          width: AppLayout.getWidth(3),
                                          height: AppLayout.getHeight(1),
                                          child: const DecoratedBox(
                                            decoration: BoxDecoration(
                                                color: Colors.white),
                                          ),
                                        )),
                              );
                            },
                          ),
                        ),
                        Center(
                            child: Transform.rotate(
                                angle: 1.5,
                                child: Icon(
                                  Icons.local_airport_rounded,
                                  color: isColor == null
                                      ? Colors.white
                                      : const Color(0xff8accf7),
                                )))
                      ])), // Spacer 와 Expanded(child: Container()) 은 같음
                      const TicketContainer(
                        isColor: true,
                      ),
                      const Spacer(),
                      Text(
                        ticket['to']['code'],
                        style: isColor == null
                            ? Styles.headLineStyle3
                                .copyWith(color: Colors.white)
                            : Styles.headLineStyle3,
                      )
                    ],
                  ),
                  const Gap(1),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      SizedBox(
                        width: AppLayout.getWidth(100),
                        child: Text(
                          ticket['from']['name'],
                          style: isColor == null
                              ? Styles.headLineStyle4
                                  .copyWith(color: Colors.white)
                              : Styles.headLineStyle4,
                        ),
                      ),
                      Text(
                        ticket['flying_time'],
                        style: isColor == null
                            ? Styles.headLineStyle4
                                .copyWith(color: Colors.white)
                            : Styles.headLineStyle4,
                      ),
                      SizedBox(
                        width: AppLayout.getWidth(100),
                        child: Text(
                          ticket['to']['name'],
                          textAlign: TextAlign.end,
                          style: isColor == null
                              ? Styles.headLineStyle4
                                  .copyWith(color: Colors.white)
                              : Styles.headLineStyle4,
                        ),
                      ),
                    ],
                  )
                ],
              ),
            ),
            /**
             * 티켓의 붉은 부분을 보여주는 뷰
             * */
            Container(
              color: isColor == null ? Styles.orangeColor : Colors.white,
              child: Row(
                children: [
                  SizedBox(
                    height: AppLayout.getHeight(20),
                    width: AppLayout.getWidth(10),
                    child: DecoratedBox(
                      decoration: BoxDecoration(
                          color: isColor == null
                              ? Colors.grey.shade200
                              : Colors.white,
                          borderRadius: BorderRadius.only(
                              topRight:
                                  Radius.circular(AppLayout.getHeight(10)),
                              bottomRight:
                                  Radius.circular(AppLayout.getHeight(10)))),
                    ),
                  ),
                  const Expanded(
                      child: Padding(
                    padding: EdgeInsets.all(6.0),
                    child: AppLayoutBuilderWidget(sections: 6,)
                  )),
                  /**
                   * 티켓의 파인 홈 표현 하는 부분
                   * */
                  SizedBox(
                    height: AppLayout.getHeight(20),
                    width: AppLayout.getWidth(10),
                    child: DecoratedBox(
                      decoration: BoxDecoration(
                          color: isColor == null
                              ? Colors.grey.shade200
                              : Colors.white,
                          borderRadius: BorderRadius.only(
                              topLeft: Radius.circular(AppLayout.getHeight(10)),
                              bottomLeft:
                                  Radius.circular(AppLayout.getHeight(10)))),
                    ),
                  )
                ],
              ),
            ),
            /**
             * 티켓의 구분선 아래에 들어갈 내용
             * */
            Container(
              decoration: BoxDecoration(
                  color: isColor == null ? Styles.orangeColor : Colors.white,
                  borderRadius: BorderRadius.only(
                      bottomLeft: Radius.circular(isColor == null? AppLayout.getHeight(21) : 0),
                      bottomRight: Radius.circular(isColor == null? AppLayout.getHeight(21) : 0))),
              padding: const EdgeInsets.only(
                  left: 16, top: 10, right: 16, bottom: 16),
              child: Column(
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      AppColumnLayout(firstText: ticket['date'], secondText: 'Date', alignment: CrossAxisAlignment.start),
                      AppColumnLayout(firstText: ticket['departure_time'], secondText: 'Departure time', alignment: CrossAxisAlignment.start),
                      AppColumnLayout(firstText: ticket['number'].toString(), secondText: 'Number', alignment: CrossAxisAlignment.start),
                    ],
                  )
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
