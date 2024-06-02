import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class StoryModel {
  final Widget widget;
  final String storyLabel;

  const StoryModel({
    required this.widget,
    required this.storyLabel,
  });
}

class StoriesPage extends StatefulWidget {
  final List<StoryModel> stories;

  const StoriesPage({
    required this.stories,
    super.key,
  });

  @override
  State<StoriesPage> createState() => _StoriesPageState();
}

class _StoriesPageState extends State<StoriesPage> {
  final pageController = PageController();
  final ValueNotifier<int> pageValueNotifier = ValueNotifier(0);

  @override
  void initState() {
    super.initState();
    pageController.addListener(() {
      final currentPageIndex = pageController.page?.toInt() ?? 0;
      if (currentPageIndex != pageValueNotifier.value) {
        pageValueNotifier.value = currentPageIndex;
      }
    });
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final List<Widget> rowContent = [];

    var pageIndex = 0;
    for (var _ in widget.stories) {
      rowContent.add(SizedBox(width: 10));
      rowContent.add(
        StoryIndicator(
          storyIndex: pageIndex,
          pageValueListenable: pageValueNotifier,
        ),
      );
      pageIndex++;
    }
    rowContent.add(SizedBox(width: 10));

    return Stack(
      children: [
        PageView(
          controller: pageController,
          children: [
            ...widget.stories.map(
              (e) => Padding(
                padding: const EdgeInsets.only(bottom: 30),
                child: Stack(
                  children: [
                    ColoredBox(
                      color: Colors.black45,
                      child: SizedBox.expand(),
                    ),
                    Center(
                      child: Padding(
                        padding: const EdgeInsets.all(30),
                        child: e.widget,
                      ),
                    ),
                    Positioned(
                      top: 20,
                      child: Padding(
                        padding: const EdgeInsets.all(20.0),
                        child: Transform.rotate(
                          angle: -0.15,
                          child: Container(
                            width: 300,
                            padding: const EdgeInsets.all(8),
                            decoration: BoxDecoration(
                              color: Colors.white.withOpacity(0.9),
                              borderRadius:
                                  BorderRadius.all(Radius.circular(32)),
                              boxShadow: [
                                BoxShadow(
                                  color: Colors.black.withOpacity(0.1),
                                  blurRadius: 10,
                                  offset: Offset(0, 10),
                                ),
                              ],
                            ),
                            child: Align(
                              alignment: Alignment.centerLeft,
                              child: Text(
                                e.storyLabel,
                                style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 20,
                                  fontWeight: FontWeight.bold,
                                ),
                                textAlign: TextAlign.start,
                              ),
                            ),
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
        Align(
          alignment: Alignment.bottomCenter,
          child: Container(
            height: 35,
            decoration: BoxDecoration(
              color: Colors.black.withOpacity(0.7),
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(6),
                topRight: Radius.circular(6),
              ),
            ),
            child: Center(
              child: SizedBox(
                height: 2,
                child: Row(
                  mainAxisSize: MainAxisSize.max,
                  children: rowContent,
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }
}

class StoryIndicator extends StatelessWidget {
  final int storyIndex;
  final ValueListenable<int> pageValueListenable;

  const StoryIndicator({
    required this.storyIndex,
    required this.pageValueListenable,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: ValueListenableBuilder<int>(
        valueListenable: pageValueListenable,
        builder: (context, value, child) {
          return ColoredBox(
            color: value + 1 > storyIndex ? Colors.white : Colors.black26,
            child: SizedBox.expand(),
          );
        },
      ),
    );
  }
}
