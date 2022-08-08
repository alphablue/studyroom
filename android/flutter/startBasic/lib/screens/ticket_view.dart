import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:startbasic/utils/app_layout.dart';
import 'package:startbasic/utils/app_styles.dart';
import 'package:startbasic/widgets/ticket_container.dart';

class TicketView extends StatelessWidget {
  const TicketView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final size = AppLayout.getSize(context);
    return SizedBox(
      width: size.width,
      height: 200,
      child: Container(
        margin: const EdgeInsets.only(left: 16),
        child: Column(
          children: [
            Container(
              decoration: const BoxDecoration(
                color: Color(0xFF526799),
                borderRadius: BorderRadius.only(
                    topLeft: Radius.circular(21),
                    topRight: Radius.circular(21)),
              ),
              padding: const EdgeInsets.all(16),
              child: Column(
                children: [
                  Row(
                    children: [
                      Text("NYC", style: Styles.headLineStyle3.copyWith(color: Colors.white),),
                      const Spacer(),
                      TicketContainer(),
                      Expanded(child: Stack(
                        children: [
                          SizedBox(
                            /**
                             *  "-" 가 화면의 크기에 따라서 동적으로 보여주기 위해서 화면의 크기를
                             *  알아야 한다. 그것을 위해서 LayoutBuilder 로 감싸준 것
                             * */
                            height:24,
                            child: LayoutBuilder(
                              builder: (BuildContext context, BoxConstraints constraints) {
                                print("The width is ${constraints.constrainWidth()}");
                                return Flex(
                                  direction: Axis.horizontal,
                                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                  mainAxisSize: MainAxisSize.max,
                                  children: List.generate((constraints.constrainWidth()/6).floor(), (index) => const SizedBox(
                                    width: 3,
                                    height: 1,
                                    child: DecoratedBox(decoration: BoxDecoration(
                                        color: Colors.white
                                    ),),
                                  )),
                                );
                              },
                            ),
                          ),
                          Center(child: Transform.rotate(angle: 1.5, child: const Icon(Icons.local_airport_rounded, color: Colors.white,)))
                        ]
                      )), // Spacer 와 Expanded(child: Container()) 은 같음
                      TicketContainer(),
                      const Spacer(),
                      Text("London", style: Styles.headLineStyle3.copyWith(color: Colors.white),)
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
