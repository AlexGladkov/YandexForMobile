import 'package:flutter/material.dart';
import 'package:yandex_pro/home/home_page.dart';

class ProMuseumModuleApp extends StatelessWidget {
  const ProMuseumModuleApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Yandex Pro',
      theme: ThemeData(
        scaffoldBackgroundColor: Colors.white,
      ),
      home: const HomePage(),
    );
  }
}
