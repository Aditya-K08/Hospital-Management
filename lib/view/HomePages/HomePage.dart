import 'package:blue_bit/view/HomePages/widgets/CustomCard.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
       // extendBodyBehindAppBar: true,
        appBar: AppBar(

          elevation: 0,
          title: Text(
            'MEDI-QUICK',
            style: TextStyle(
              fontWeight: FontWeight.w500,
              fontSize: 16,
            ),
          ),
          actions: [
            IconButton(
              icon: Icon(Icons.search),
              onPressed: () {
                // Handle search functionality here
              },
            ),
          ],
        ),
        //backgroundColor: Color(0xFF1E1E1E),
        body: Padding(
          padding: const EdgeInsets.all(16.0),
          child: SingleChildScrollView(
            child: Column(
              children: [
                SizedBox(height: 20),
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: TextField(
                    decoration: InputDecoration(
                      hintText: 'Search...',
                      prefixIcon: Icon(Icons.search),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(8.0),
                      ),
                    ),
                    onChanged: (query) {
                      // Handle search query changes
                    },
                  ),
                ),
                SizedBox(height : 16.h),
                CustomCard(),
                SizedBox(height : 16.h),
                CustomCard(),
                SizedBox(height : 16.h),
                CustomCard(),
                SizedBox(height : 16.h),
                CustomCard(),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
