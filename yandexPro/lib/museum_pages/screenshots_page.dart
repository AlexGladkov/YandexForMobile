import 'package:flutter/material.dart';
import 'package:yandex_pro/ui_kit/stories_page.dart';

class ScreenshotsPage extends StatefulWidget {
  const ScreenshotsPage({super.key});

  @override
  State<ScreenshotsPage> createState() => _ScreenshotsPageState();
}

class _ScreenshotsPageState extends State<ScreenshotsPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: StoriesPage(
        stories: [
          StoryModel(
            widget: Image.asset('assets/pro_screenshots/screenshot_2.png'),
            storyLabel:
                'Это экран настроек. В приложении множество настроек которые позволяют настроить его под себя.',
          ),
          StoryModel(
            widget: Image.asset('assets/pro_screenshots/screenshot_1.png'),
            storyLabel: 'Так выглядит экран входа в приложение.',
          ),
          StoryModel(
            widget: Image.asset('assets/pro_screenshots/screenshot_2.png'),
            storyLabel:
                'Это экран настроек. В приложении множество настроек которые позволяют настроить его под себя.',
          ),
          StoryModel(
            widget: Image.asset('assets/pro_screenshots/screenshot_1.png'),
            storyLabel: 'Так выглядит экран входа в приложение.',
          ),
        ],
      ),
    );
  }
}
