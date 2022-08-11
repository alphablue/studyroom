import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:gap/gap.dart';
import 'package:startbasic/utils/app_layout.dart';
import 'package:startbasic/utils/app_styles.dart';
import 'package:startbasic/widgets/ticket_container.dart';

class TicketView extends StatelessWidget {
  final Map<String, dynamic> ticket;

  const TicketView({Key? key, required this.ticket}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final size = AppLayout.getSize(context);
    return SizedBox(
      /**
       * 티켓이 화면 전체를 채우는 것 보다 일부분은 덜채워서 여러개가 있다는 것을 보여 주는
       * 것이 더 직관적이다.
       * */
      width: size.width * 0.85,
      height: AppLayout.getHeight(200),
      child: Container(
        margin: EdgeInsets.only(right: AppLayout.getHeight(16)),
        child: Column(
          children: [
            /**
             * 티켓의 파란부분을 보여주는 뷰
             * */
            Container(
              decoration:  BoxDecoration(
                color: Color(0xFF526799),
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
                        style:
                            Styles.headLineStyle3.copyWith(color: Colors.white),
                      ),
                      const Spacer(),
                      TicketContainer(),
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
                                child: const Icon(
                                  Icons.local_airport_rounded,
                                  color: Colors.white,
                                )))
                      ])), // Spacer 와 Expanded(child: Container()) 은 같음
                      TicketContainer(),
                      const Spacer(),
                      Text(
                        ticket['to']['code'],
                        style:
                            Styles.headLineStyle3.copyWith(color: Colors.white),
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
                          style: Styles.headLineStyle4
                              .copyWith(color: Colors.white),
                        ),
                      ),
                      Text(
                        ticket['flying_time'],
                        style:
                            Styles.headLineStyle4.copyWith(color: Colors.white),
                      ),
                      SizedBox(
                        width: AppLayout.getWidth(100),
                        child: Text(
                          ticket['to']['name'],
                          textAlign: TextAlign.end,
                          style: Styles.headLineStyle4
                              .copyWith(color: Colors.white),
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
              color: Styles.orangeColor,
              child: Row(
                children: [
                  SizedBox(
                    height: AppLayout.getHeight(20),
                    width: AppLayout.getWidth(10),
                    child: DecoratedBox(
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.only(
                              topRight: Radius.circular(AppLayout.getHeight(10)),
                              bottomRight: Radius.circular(AppLayout.getHeight(10)))),
                    ),
                  ),
                  Expanded(
                      child: Padding(
                    padding: const EdgeInsets.all(12.0),
                    child: LayoutBuilder(
                      builder:
                          (BuildContext context, BoxConstraints constraints) {
                        return Flex(
                          direction: Axis.horizontal,
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          mainAxisSize: MainAxisSize.max,
                          children: List.generate(
                              (constraints.constrainWidth() / 15).floor(),
                              (index) => SizedBox(
                                    width: AppLayout.getWidth(5),
                                    height: AppLayout.getHeight(1),
                                    child: const DecoratedBox(
                                      decoration:
                                          BoxDecoration(color: Colors.white),
                                    ),
                                  )),
                        );
                      },
                    ),
                  )),
                  SizedBox(
                    height: AppLayout.getHeight(20),
                    width: AppLayout.getWidth(10),
                    child: DecoratedBox(
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.only(
                              topLeft: Radius.circular(AppLayout.getHeight(10)),
                              bottomLeft: Radius.circular(AppLayout.getHeight(10)))),
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
                  color: Styles.orangeColor,
                  borderRadius: BorderRadius.only(
                      bottomLeft: Radius.circular(AppLayout.getHeight(21)),
                      bottomRight: Radius.circular(AppLayout.getHeight(21)))),
              padding: const EdgeInsets.only(left: 16, top: 10, right: 16, bottom: 16),
              child: Column(
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(ticket['date'],
                          style: Styles.headLineStyle3.copyWith(color: Colors.white),),
                          const Gap(5),
                          Text("Date",
                          style: Styles.headLineStyle4.copyWith(color: Colors.white),)
                        ],
                      ),
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Text(ticket['departure_time'],
                          style: Styles.headLineStyle3.copyWith(color: Colors.white),),
                          const Gap(5),
                          Text("Departure time",
                          style: Styles.headLineStyle4.copyWith(color: Colors.white),)
                        ],
                      ),
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.end,
                        children: [
                          Text(ticket['number'].toString(),
                          style: Styles.headLineStyle3.copyWith(color: Colors.white),),
                          const Gap(5),
                          Text("Number",
                          style: Styles.headLineStyle4.copyWith(color: Colors.white),)
                        ],
                      ),
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
