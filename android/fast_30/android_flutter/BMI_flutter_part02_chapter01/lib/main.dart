import 'dart:math';

import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      debugShowCheckedModeBanner: false,
      title: "bmi app",
      home: BmiHome()
    );
  }
}


class BmiHome extends StatefulWidget {
  const BmiHome({Key? key}) : super(key: key);

  @override
  State<BmiHome> createState() => _BmiHomeState();
}

class _BmiHomeState extends State<BmiHome> {

  final height = TextEditingController();
  final weight = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('bmi app')),
      body: Container(
        padding: const EdgeInsets.all(30),
        child: Center(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text('신장'),
              TextField(
                controller: height,
                keyboardType: TextInputType.number,
              ),
              const Text('체중'),
              TextField(
                controller: weight,
                keyboardType: TextInputType.number,
              ),
              TextButton(onPressed: () {
                final double result =
                  double.parse(weight.text) / pow(double.parse(height.text) / 100, 2);

                Navigator.push(
                    context,
                  MaterialPageRoute(builder: (context) => ResultView(resultData: result))
                );
              }, child: const Text("결과 보기"))
            ],
          ),
        ),
      ),
    );
  }
}

class ResultView extends StatelessWidget {
  final double resultData;

  const ResultView({Key? key, required this.resultData}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Text(resultText(resultData))
      ),
    );
  }

  String resultText (double data) {
    String dd = "";

    if(data >= 35.0) {
      dd = "고도 비만";
    } else if (data >= 30.0) {
      dd = "중정도 비만";
    } else if (data >= 25.0) {
      dd = "경도 비만";
    } else if (data >= 23.0) {
      dd = "과체중";
    } else if (data >= 18.5) {
      dd = "정상체중";
    } else {
      dd = "저체중";
    }

    return dd;
  }
}


