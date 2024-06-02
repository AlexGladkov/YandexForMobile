import 'package:flutter/material.dart';

import '../museum_pages/export.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final pageController = PageController();

  @override
  void dispose() {
    pageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: PageView(
          controller: pageController,
          scrollDirection: Axis.vertical,
          children: [
            WelcomePage(
              onGoTap: () {
                pageController.nextPage(
                  duration: Durations.extralong2,
                  curve: Curves.easeIn,
                );
              },
            ),
            ScreenshotsPage(),
            HistoryPage(),
            //TODO:
            //TeamPage(),
            TechnologyPage(),
            // TODO:
            // GamePage(),
          ],
        ),
      ),
    );
  }
}
