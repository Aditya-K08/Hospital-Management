
import 'package:blue_bit/presentation/widgets/time_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:intl/intl.dart';

class AvailScreen extends StatefulWidget {
  const AvailScreen({super.key});

  @override
  State<AvailScreen> createState() => _AvailScreenState();
}

class _AvailScreenState extends State<AvailScreen> {
  GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  GlobalKey _startHourKey = GlobalKey();
  GlobalKey _endHourKey = GlobalKey();

  ScrollController _scrollController = ScrollController();
  User? user = FirebaseAuth.instance.currentUser;
  String gender = '';
  bool isgendernotselected = false;
  DateTime? selectedYear;
  List<String> SelectedDays = [];

  DateTime? selectedMonth;
  bool _isautovalidate = false;
  bool isSitter = true;
  bool _isLoading = false;
  bool _isskip = false;



  @override
  void initState() {
    super.initState();
    DateTime now = DateTime.now();
    start_hour = '${now.hour % 12 == 0 ? 12 : now.hour % 12}'.padLeft(2, '0');
    selectedFromTime =
        '${now.hour % 12 == 0 ? 12 : now.hour % 12} '.padLeft(2, '0') +
            '${now.minute}'.padLeft(2, '0') +
            (now.hour < 12 ? 'AM' : 'PM');
    start_minute = '${now.minute}'.padLeft(2, '0');
    start_ampm = now.hour < 12 ? 'AM' : 'PM';
  }

  List<int> selectedIndices = [];
  List<String> days = [
    'Mon',
    'Tue',
    'Wed',
    'Thu',
    'Fri',
    'Sat',
    'Sun',
  ];
  String selected_start_Date = '';
  String selected_end_Date = '';
  String start_time = '';
  String start_hour = '';
  String start_minute = '';
  String start_second = '';
  String start_ampm = '';
  String end_hour = '';
  String end_minute = '';
  String end_second = '';
  String end_ampm = '';
  String start_time_dummy = '00:00:00 PM';
  String end_time = '';
  String end_time_dummy = '00:00:00 PM';
  DateTime datetime_s = DateTime.now();
  DateTime datetime_e = DateTime.now();
  bool starthour_clicked = false;
  bool endhour_clicked = false;
  bool areDaysSelected = false;
  String selectedFromTime = '';
  String selectedToTime = '';

  int activeDropdown = 0;
  TextEditingController endhourcontroller = TextEditingController();
  TextEditingController starthourcontroller = TextEditingController();

  bool isTimeRangeValid() {
    if (start_hour.isEmpty ||
        start_minute.isEmpty ||
        start_ampm.isEmpty ||
        end_hour.isEmpty ||
        end_minute.isEmpty ||
        end_ampm.isEmpty) {
      return false;
    }

    DateTime startTime =
        DateTime(2022, 1, 1, int.parse(start_hour), int.parse(start_minute));
    DateTime endTime =
        DateTime(2022, 1, 1, int.parse(end_hour), int.parse(end_minute));
    return endTime.isAfter(startTime);
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: GestureDetector(
        onTap: () {
          setState(() {
            starthour_clicked = false;
            endhour_clicked = false;
          });
          FocusScope.of(context).requestFocus(FocusNode());
        },
        child: Scaffold(
          body: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            decoration: const BoxDecoration(
              gradient: LinearGradient(
                begin: Alignment(0.00, -1.00),
                end: Alignment(0, 1),
                colors: [Color(0xFFDFF1FA), Color(0xFF8BD4F9)],
              ),
            ),
            child: Stack(
              alignment: Alignment.topCenter,
              children: [
                Positioned(
                    top: MediaQuery.of(context).size.height * 0.085,
                    child: Image.asset(
                      'assets/images/homebg.png',
                      width: MediaQuery.of(context).size.width,
                      height: MediaQuery.of(context).size.height * 0.8,
                      fit: BoxFit.fill,
                    )),
                Padding(
                  padding: EdgeInsets.only(left: 10.w, right: 10.w, top: 16.h),
                  child: SingleChildScrollView(
                    controller: _scrollController,
                    child: Column(
                      children: [
                        Align(
                          alignment: Alignment.centerLeft,
                          child: IconButton(
                            onPressed: () {
                              Navigator.pop(context);
                            },
                            icon: Icon(Icons.arrow_back_ios),
                          ),
                        ),
                        Padding(
                          padding: EdgeInsets.only(
                              left: 10.w, right: 10.w, top: 16.h),
                          child: Align(
                            alignment: Alignment.centerLeft,
                            child: Text(
                              'Availablity',
                              style: TextStyle(
                                fontSize: 24,
                                fontWeight: FontWeight.w600,
                                color: Colors.black,
                                fontFamily: 'Poppins',
                              ),
                              textAlign: TextAlign.left,
                            ),
                          ),
                        ),
                        Padding(
                          padding: EdgeInsets.only(
                              left: 10.w, right: 10.w, top: 16.h),
                          child: Align(
                            alignment: Alignment.centerLeft,
                            child: Text(
                              'Select Booking accepting days',
                              style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w600,
                                  fontFamily: 'Poppins'),
                            ),
                          ),
                        ),
                        Padding(
                          padding: EdgeInsets.only(
                              left: 10.w, right: 10.w, bottom: 8.h),
                          child: Container(
                            height: ScreenUtil().setHeight(200),
                            child: GridView.builder(
                              gridDelegate:
                                  SliverGridDelegateWithFixedCrossAxisCount(
                                crossAxisCount: 3,
                                crossAxisSpacing: 4.w,
                                mainAxisSpacing: 8.h,
                                childAspectRatio: 96.w / 51.h,
                              ),
                              itemBuilder: (context, index) {
                                return CustomContainer(
                                  text: days[index],
                                  onTap: () {
                                    setState(() {
                                      if (selectedIndices.contains(index)) {
                                        selectedIndices.remove(index);
                                        SelectedDays.remove(days[index]);
                                        print('click');
                                      } else {
                                        selectedIndices.add(index);
                                        SelectedDays.add(days[index]);
                                        print('unclick');
                                      }
                                      areDaysSelected =
                                          selectedIndices.isNotEmpty;
                                      print('selected + ${areDaysSelected}');
                                    });
                                  },
                                );
                              },
                              itemCount: 7,
                            ),
                          ),
                        ),
                        Padding(
                          padding: EdgeInsets.only(
                              left: 10.w, right: 10.w, top: 16.h),
                          child: Align(
                            alignment: Alignment.centerLeft,
                            child: Text(
                              'Select Booking accepting Time',
                              style: TextStyle(
                                fontSize: 16.h,
                                fontWeight: FontWeight.w600,
                                color: Colors.black,
                                fontFamily: 'Poppins',
                              ),
                            ),
                          ),
                        ),
                        SizedBox(
                          height: 8.h,
                        ),
                        Column(
                          children: [
                            InkWell(
                                key: _startHourKey,
                                onTap: () => setState(() {
                                      starthour_clicked = !starthour_clicked;
                                    }),
                                child: _buildholder_start_hour('From')),
                            if (starthour_clicked)
                              TimePicker(
                                sethour: (val1) {
                                  setState(() {
                                    start_hour = val1;
                                    selectedFromTime =
                                        '$start_hour:$start_minute:$start_second $start_ampm';
                                  });
                                },
                                setminute: (val) {
                                  setState(() {
                                    start_minute = val;
                                    selectedFromTime =
                                        '$start_hour:$start_minute:$start_second $start_ampm';
                                  });
                                },
                                // setsecond: (val) {
                                //   setState(() {
                                //     start_second = val;
                                //   });
                                // },
                                setampm: (val) {
                                  setState(() {
                                    start_ampm = val;
                                    selectedFromTime =
                                        '$start_hour:$start_minute:$start_second $start_ampm';
                                  });
                                },
                                initialHour: start_hour.isNotEmpty
                                    ? int.parse(start_hour)
                                    : int.parse(start_time_dummy.split(':')[0]),
                                initialMinute: start_minute.isNotEmpty
                                    ? int.parse(start_minute)
                                    : int.parse(start_time_dummy.split(':')[1]),
                                // initialSecond: start_second.isNotEmpty
                                //     ? int.parse(start_second)
                                //     : int.parse(start_time_dummy
                                //         .split(' ')[0]
                                //         .split(':')[2]),
                                initialAmOrPm: start_ampm.isEmpty
                                    ? 0
                                    : start_ampm == 'AM'
                                        ? 1
                                        : 0,
                              )
                          ],
                        ),
                        SizedBox(
                          height: 16.h,
                        ),
                        Column(
                          children: [
                            InkWell(
                                key: _endHourKey,
                                onTap: () => setState(() {
                                      // activeDropdown = activeDropdown == 2 ? 0 : 2;
                                      //starthour_clicked = false;
                                      endhour_clicked = !endhour_clicked;
                                    }),
                                child: _buildholder_end_hour('To')),
                            if (endhour_clicked)
                              TimePicker(
                                sethour: (val1) {
                                  setState(() {
                                    end_hour = val1;
                                    selectedToTime =
                                        '$start_hour:$start_minute:$start_second $start_ampm';
                                  });
                                },
                                setminute: (val) {
                                  setState(() {
                                    end_minute = val;
                                    selectedToTime =
                                        '$start_hour:$start_minute:$start_second $start_ampm';
                                  });
                                },
                                // setsecond: (val) {
                                //   setState(() {
                                //     end_second = val;
                                //   });
                                // },
                                setampm: (val) {
                                  setState(() {
                                    end_ampm = val;
                                    selectedToTime =
                                        '$start_hour:$start_minute:$start_second $start_ampm';
                                  });
                                },
                                initialHour: end_hour.isNotEmpty
                                    ? int.parse(end_hour)
                                    : int.parse(end_time_dummy.split(':')[0]),
                                initialMinute: end_minute.isNotEmpty
                                    ? int.parse(end_minute)
                                    : int.parse(end_time_dummy.split(':')[1]),
                                // initialSecond: end_second.isNotEmpty
                                //     ? int.parse(end_second)
                                //     : int.parse(end_time_dummy
                                //         .split(' ')[0]
                                //         .split(':')[2]),
                                initialAmOrPm: end_ampm.isEmpty
                                    ? 0
                                    : end_ampm == 'AM'
                                        ? 1
                                        : 0,
                              )
                          ],
                        ),
                        SizedBox(
                          height: 16.h,
                        ),
                        _buildsaveandcontinue(context),
                      ],
                    ),
                  ),
                ),
                if (_isLoading) LoadingWidget(),
              ],
            ),
          ),
        ),
      ),
    );
  }

  // Widget _buildsaveandcontinue(BuildContext context) {
  //   return FutureBuilder(
  //       future: AuthMethods().getSitterDetails(user!.uid),
  //       builder: (context, snapshot) {
  //         print(isSitter);
  //         if (snapshot.hasData) {
  //           if (isSitter) {
  //             return InkWell(
  //               onTap: _addDetails,
  //               child: CustomButtonAuthWidget(txt: 'Save & Continue'),
  //             );
  //           }
  //         } else {
  //           return Container();
  //         }
  //         return Container();
  //       });
  // }
  Widget _buildsaveandcontinue(BuildContext context) {
    return FutureBuilder(
      future: AuthMethods().getSitterDetails(user!.uid),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.done) {
          if (snapshot.hasData) {
            if (isSitter) {
              return Column(
                children: [
                  InkWell(
                    onTap: () {
                      if (!areDaysSelected) {
                        ScaffoldMessenger.of(context).showSnackBar(
                          SnackBar(
                            content: Text('Please Select At Least One Day'),
                            backgroundColor: Color.fromARGB(255, 91, 178, 228),
                          ),
                        );
                      } else if (isTimeRangeValid()) {
                        print('Executing _addDetails...');
                        // _addDetails();
                        print('Navigating to BookingScreen');
                      } else {
                        // Show a dialog for invalid time range
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return CustomAlertDialog(
                              title: 'Invalid Time Range',
                              content:
                                  'Please choose a time later than the one selected above',
                              onClose: () {
                                Navigator.of(context).pop();
                              },
                            );
                          },
                        );
                      }
                    },
                    child: CustomButtonAuthWidget(txt: 'Save & Continue'),
                  ),
                ],
              );
            }
          } else {
            // Handle the case when snapshot does not have data
            print('Snapshot does not have data');
          }
        } else {
          // Handle other connection states
          print('Connection state: ${snapshot.connectionState}');
          return Container();
        }
        return Container();
      },
    );
  }

  Container _buildholder_start_hour(String msg) {
    return Container(
      width: 312.w,
      height: 51.h,
      // padding: const EdgeInsets.only(left: 18, right: 16),
      clipBehavior: Clip.antiAlias,
      decoration: ShapeDecoration(
        color: Colors.white.withOpacity(0.5),
        shape: RoundedRectangleBorder(
          side: BorderSide(width: 1.w, color: Color(0xFF8ED5F9)),
          borderRadius: starthour_clicked
              ? BorderRadius.only(
                  topLeft: Radius.circular(20), topRight: Radius.circular(20))
              : BorderRadius.circular(20.w),
        ),
      ),
      child: Padding(
        padding: EdgeInsets.symmetric(horizontal: 5.w, vertical: 5.h),
        child: Row(
          children: [
            Container(
              width: 247.w,
              padding: EdgeInsets.only(left: 13.w),
              child: start_hour.isEmpty &&
                      start_minute.isEmpty &&
                      start_second.isEmpty &&
                      start_ampm.isEmpty
                  ? Text(
                      msg,
                      textAlign: TextAlign.left,
                      style: TextStyle(
                        color: Color(0xFF6F6F6F),
                        fontSize: 16.sp,
                        fontFamily: 'Poppins',
                        fontWeight: FontWeight.w400,
                      ),
                    )
                  : Text(
                      '$start_hour : $start_minute : $start_second $start_ampm',
                      textAlign: TextAlign.left,
                      style: TextStyle(
                        color: Color(0xFF000072),
                        fontSize: 16.sp,
                        fontFamily: 'Poppins',
                        fontWeight: FontWeight.w400,
                      ),
                    ),
            ),
            DropDownWidget()
          ],
        ),
      ),
    );
  }

  Container _buildholder_end_hour(String msg) {
    return Container(
      width: 312.w,
      height: 51.h,
      // padding: const EdgeInsets.only(left: 18, right: 16),
      clipBehavior: Clip.antiAlias,
      decoration: ShapeDecoration(
        color: Colors.white.withOpacity(0.5),
        shape: RoundedRectangleBorder(
          side: BorderSide(width: 1.w, color: Color(0xFF8ED5F9)),
          borderRadius: endhour_clicked
              ? BorderRadius.only(
                  topLeft: Radius.circular(20), topRight: Radius.circular(20))
              : BorderRadius.circular(20.w),
        ),
      ),
      child: Padding(
        padding: EdgeInsets.symmetric(horizontal: 5.w, vertical: 5.h),
        child: Row(
          // mainAxisAlignment:
          //     MainAxisAlignment.spaceBetween,
          children: [
            Container(
              width: 247.w,
              padding: EdgeInsets.only(left: 13.w),
              child: end_hour.isEmpty &&
                      end_minute.isEmpty &&
                      end_second.isEmpty &&
                      end_ampm.isEmpty
                  ? Text(
                      msg,
                      textAlign: TextAlign.left,
                      style: TextStyle(
                        color: Color(0xFF6F6F6F),
                        fontSize: 16.sp,
                        fontFamily: 'Poppins',
                        fontWeight: FontWeight.w400,
                      ),
                    )
                  : Text(
                      '$end_hour : $end_minute : $end_second $end_ampm',
                      textAlign: TextAlign.left,
                      style: TextStyle(
                        color: Color(0xFF000072),
                        fontSize: 16.sp,
                        fontFamily: 'Poppins',
                        fontWeight: FontWeight.w400,
                      ),
                    ),
            ),
            DropDownWidget()
          ],
        ),
      ),
    );
  }
}
