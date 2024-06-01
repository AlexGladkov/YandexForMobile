import 'package:flutter/material.dart';
import 'package:yandex_pro/ui_kit/export.dart';

class HistoryPage extends StatefulWidget {
  const HistoryPage({super.key});

  @override
  State<HistoryPage> createState() => _HistoryPageState();
}

class _HistoryPageState extends State<HistoryPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: StoriesPage(
        stories: [
          StoryModel(
            widget: Image.asset('assets/pro_screenshots/old_taximeter.png'),
            storyLabel:
                'Так выглядел экран в первых версиях таксометра; Согласитесь, приложение сильно изменилось с тех пор.',
          ),
          StoryModel(
            widget:
                Image.asset('assets/pro_screenshots/semi_old_taximeter.png'),
            storyLabel:
                'Следующая итерация - появляется шторка и кнопка "На линию"',
          ),
          StoryModel(
            widget: Image.asset('assets/pro_screenshots/kotlin_taximeter.png'),
            storyLabel:
                'А это экран таксометра на kotlin. Дизайн уже очень похож на существующее приложение.',
          ),
          StoryModel(
            widget: Image.asset('assets/pro_screenshots/flutter_taximeter.png'),
            storyLabel: 'Таксометр сейчас, на Flutter.',
          ),
        ],
      ),
    );
  }
}
