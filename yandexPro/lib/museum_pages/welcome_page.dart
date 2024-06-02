import 'package:flutter/material.dart';
import 'package:yandex_pro/ui_kit/export.dart';

class WelcomePage extends StatefulWidget {
  final VoidCallback onGoTap;

  const WelcomePage({
    required this.onGoTap,
    super.key,
  });

  @override
  State<WelcomePage> createState() => _WelcomePageState();
}

class _WelcomePageState extends State<WelcomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Center(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Spacer(),
              Text(
                'Яндекс Про',
                style: TextStyle(
                  color: Colors.black,
                  fontSize: 46,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 5),
              Text(
                'Приложение-музей',
                style: TextStyle(
                  color: Colors.black,
                  fontSize: 30,
                  fontWeight: FontWeight.w400,
                ),
              ),
              const SizedBox(height: 40),
              SizedBox.square(
                dimension: 160,
                child: Image.asset('assets/logo/pro-logo.jpg'),
              ),
              const SizedBox(height: 20),
              Text(
                '''Яндекс Про — это приложение для водителей, курьеров, сборщиков и других исполнителей.''',
                style: TextStyle(
                  fontSize: 17,
                  fontWeight: FontWeight.w500,
                ),
              ),
              const SizedBox(height: 5),
              Text(
                '''Мы помогаем работать с сервисами Яндекса в России и за её пределами: выполнять заказы, отслеживать заработок, проходить проверки качества и т. д.''',
                style: TextStyle(
                  fontSize: 17,
                  fontWeight: FontWeight.w500,
                ),
              ),
              Spacer(flex: 5),
              PrimaryButton(
                text: 'Вперед!',
                onTap: widget.onGoTap,
              ),
              Spacer(),
            ],
          ),
        ),
      ),
    );
  }
}
