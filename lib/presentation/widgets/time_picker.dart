// ignore_for_file: public_member_api_docs, sort_constructors_first
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'package:GoCarly/core/app_export.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class TimePicker extends StatefulWidget {
  Function sethour;
  Function setminute;
  // Function setsecond;
  Function setampm;
  int initialHour;
  int initialMinute;
  int initialSecond;
  int initialAmOrPm;

  TimePicker({
    Key? key,
    required this.sethour,
    required this.setminute,
    //   required this.setsecond,
    required this.setampm,
    this.initialHour = 0,
    this.initialMinute = 0,
    this.initialSecond = 0,
    this.initialAmOrPm = 0,
  }) : super(key: key);

  @override
  State<TimePicker> createState() => _TimePickerState();
}

class _TimePickerState extends State<TimePicker> {
  int currentSelectedHour = 0;
  String currentSelectedHour_s = '';
  int currentSelectedMinute = 0;
  String currentSelectedMinute_s = '';
  int currentSelectedSecond = 0;
  String currentSelectedSecond_s = '';
  int currentSelectedAmOrPm = 0;
  String am_or_pm = '';
  FixedExtentScrollController? hourController;
  FixedExtentScrollController? minuteController;
  FixedExtentScrollController? secondController;
  FixedExtentScrollController? ampmController;

  @override
  void initState() {
    super.initState();
    // Set initial values when the widget is initialized
    currentSelectedHour = widget.initialHour;
    currentSelectedMinute = widget.initialMinute;
    currentSelectedSecond = widget.initialSecond;
    currentSelectedAmOrPm = widget.initialAmOrPm;
    am_or_pm = currentSelectedAmOrPm == 0 ? 'PM' : 'AM';
    hourController =
        FixedExtentScrollController(initialItem: currentSelectedHour);
    minuteController =
        FixedExtentScrollController(initialItem: currentSelectedMinute);
    secondController =
        FixedExtentScrollController(initialItem: currentSelectedSecond);
    ampmController =
        FixedExtentScrollController(initialItem: currentSelectedAmOrPm);
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 312.w,
      height: 236.h,
      padding: EdgeInsets.symmetric(
        horizontal: 25.w,
      ),
      clipBehavior: Clip.antiAlias,
      decoration: ShapeDecoration(
        color: const Color(0xFFE5F6FF),
        shape: RoundedRectangleBorder(
          side: BorderSide(
            color: appTheme.blue20001,
            width: 1.w,
          ),
          borderRadius: const BorderRadius.only(
            bottomLeft: Radius.circular(25),
            bottomRight: Radius.circular(25),
          ),
        ),
        shadows: const [
          BoxShadow(
            color: Color(0x3FD2CECE),
            blurRadius: 10,
            offset: Offset(0, 4),
            spreadRadius: 0,
          )
        ],
      ),
      child: Stack(
        alignment: Alignment.center,
        children: [
          Container(
            width: 312.w,
            height: 200.h,
            padding: EdgeInsets.symmetric(
              horizontal: 10.w,
            ),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Divider(
                  color: Color(0xFF4D4D4D),
                ),
                Divider(
                  color: Color(0xFF4D4D4D),
                ),
              ],
            ),
          ),
          Row(
            children: [
              _buildHourList(12),
              _buildseparator(),
              _buildMinuteList(60),
              _buildseparator(),
              // _buildSecondList(60),
              _buildAmPmListWheelScrollView('PM', "AM"),
            ],
          ),
        ],
      ),
    );
  }

  SizedBox _buildseparator() {
    return SizedBox(
      width: 8.w,
      height: 24.h,
      child: Text(
        ':',
        textAlign: TextAlign.center,
        style: TextStyle(
          color: Color(0xFF000072),
          fontSize: 20.sp,
          fontFamily: 'Inter',
          fontWeight: FontWeight.w500,
          height: 0,
        ),
      ),
    );
  }

  Widget _buildHourList(int itemCount) {
    return Container(
      width: 50.w,
      height: 200.h,
      child: ListWheelScrollView(
        controller: hourController,
        itemExtent: 60.h,
        diameterRatio: 10,
        physics: FixedExtentScrollPhysics(),
        onSelectedItemChanged: (index) {
          setState(() {
            currentSelectedHour = index;
            currentSelectedHour_s = index.toString().padLeft(2, '0');
            widget.sethour(currentSelectedHour_s
                //   '$currentSelectedHour_s : $currentSelectedMinute_s : $currentSelectedSecond_s $am_or_pm');
                );
          });
        },
        children: List.generate(itemCount, (index) {
          // Determine the color based on whether the index is the current selected index
          Color textColor = (index == currentSelectedHour)
              ? Color(0xFF000072)
              : Color(0xFF4D4D4D);

          return Center(
            child: SizedBox(
              width: 38.w,
              height: 24.h,
              child: Text(
                "${index.toString().padLeft(2, '0')}",
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: textColor,
                  fontSize: 20.sp,
                  fontFamily: 'Inter',
                  fontWeight: FontWeight.w600,
                  //  height: 0,
                ),
              ),
            ),
          );
        }),
      ),
    );
  }

  Widget _buildMinuteList(int itemCount) {
    return Container(
      width: 50.w,
      height: 200.h,
      child: ListWheelScrollView(
        controller: minuteController,
        itemExtent: 60.h,
        diameterRatio: 10,
        physics: FixedExtentScrollPhysics(),
        onSelectedItemChanged: (index) {
          setState(() {
            currentSelectedMinute = index;
            currentSelectedMinute_s = index.toString().padLeft(2, '0');
            widget.setminute(
              currentSelectedMinute_s,
              //   currentSelectedSecond_s, am_or_pm
              //   '$currentSelectedHour_s : $currentSelectedMinute_s : $currentSelectedSecond_s $am_or_pm');
            );
          });
        },
        children: List.generate(itemCount, (index) {
          // Determine the color based on whether the index is the current selected index
          Color textColor = (index == currentSelectedMinute)
              ? Color(0xFF000072)
              : Color(0xFF4D4D4D);

          return Center(
            child: SizedBox(
              width: 38.w,
              height: 24.h,
              child: Text(
                "${index.toString().padLeft(2, '0')}",
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: textColor,
                  fontSize: 20.sp,
                  fontFamily: 'Inter',
                  fontWeight: FontWeight.w600,
                  //  height: 0,
                ),
              ),
            ),
          );
        }),
      ),
    );
  }

  Widget _buildSecondList(int itemCount) {
    return Container(
      width: 50.w,
      height: 200.h,
      child: ListWheelScrollView(
        controller: secondController,
        itemExtent: 60.h,
        diameterRatio: 10,
        physics: FixedExtentScrollPhysics(),
        onSelectedItemChanged: (index) {
          setState(() {
            currentSelectedSecond = index;
            currentSelectedSecond_s = index.toString().padLeft(2, '0');
          });
        },
        children: List.generate(itemCount, (index) {
          // Determine the color based on whether the index is the current selected index
          Color textColor = (index == currentSelectedSecond)
              ? Color(0xFF000072)
              : Color(0xFF4D4D4D);

          return Center(
            child: SizedBox(
              width: 38.w,
              height: 24.h,
              child: Text(
                "${index.toString().padLeft(2, '0')}",
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: textColor,
                  fontSize: 20.sp,
                  fontFamily: 'Inter',
                  fontWeight: FontWeight.w600,
                  //  height: 0,
                ),
              ),
            ),
          );
        }),
      ),
    );
  }

  Widget _buildAmPmListWheelScrollView(String msg, String msg1) {
    return Container(
      width: 80.w,
      height: 200.h,
      child: ListWheelScrollView(
        controller: ampmController,
        itemExtent: 60.h,
        diameterRatio: 10,
        physics: FixedExtentScrollPhysics(),
        onSelectedItemChanged: (index) {
          setState(() {
            currentSelectedAmOrPm = index;
            am_or_pm = currentSelectedAmOrPm == 0 ? 'PM' : 'AM';
            widget.setampm(
                // currentSelectedHour_s, currentSelectedMinute_s,
                //   currentSelectedSecond_s,
                am_or_pm
                //   '$currentSelectedHour_s : $currentSelectedMinute_s : $currentSelectedSecond_s $am_or_pm');
                );
          });
        },
        children: [
          build_am_or_pm(msg, currentSelectedAmOrPm == 0),
          build_am_or_pm(msg1, currentSelectedAmOrPm == 1),
        ],
      ),
    );
  }

  Widget build_am_or_pm(String msg, bool isSelected) {
    return Container(
      width: 38.w,
      height: 24.h,
      alignment: Alignment.center,
      child: Text(
        msg,
        textAlign: TextAlign.center,
        style: TextStyle(
          color: isSelected ? Color(0xFF000072) : Color(0xFF4D4D4D),
          fontSize: 20.sp,
          fontFamily: 'Inter',
          fontWeight: FontWeight.w600,
          //  height: 0,
        ),
      ),
    );
  }
}
