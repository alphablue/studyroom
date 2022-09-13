import 'package:blocstudy/configs/themes/app_colors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MainButton extends StatelessWidget {
  const MainButton(
      {Key? key,
      this.title = "",
      required this.onTap,
      this.enabled = true,
      this.child,
      this.color})
      : super(key: key);

  final String title;
  final VoidCallback onTap;
  final enabled;
  final Widget? child;
  final Color? color;

  @override
  Widget build(BuildContext context) {
    return Material(
      /// boxdecoration 만 줬을 때는 적용이 안됐다가 type을 주니 각 모서리에 radius가
      /// 적용됨
      type: MaterialType.transparency,
      child: SizedBox(
        height: 55,
        child: InkWell(
          onTap: enabled == false? null: onTap,
          child: Container(
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(15),
              color: color??Theme.of(context).cardColor
            ),
            width: double.maxFinite,
            child: Padding(
              padding: const EdgeInsets.all(10.0),
              child: child??Center(
                  child: Text(title, style: const TextStyle(
                      fontWeight: FontWeight.bold,
                      color: onSurfaceTextColor
                  ),
              )),
            ),
          ),
        ),
      ),
    );
  }
}
