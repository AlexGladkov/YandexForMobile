import 'package:flutter/material.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:yandex_pro/ui_kit/export.dart';
import 'package:yandex_pro/ui_kit/card.dart';

class TechnologyPage extends StatefulWidget {
  const TechnologyPage({super.key});

  @override
  State<TechnologyPage> createState() => _TechnologyPageState();
}

class _TechnologyPageState extends State<TechnologyPage> {
  final talksTumbnailUrls = <_PublicTalkModel>[
    _PublicTalkModel(
      title: 'Yet another DI',
      talkUrl: '',
      tumbnailUrl: 'assets/talks_tumbnails/yet_di.webp',
    ),
    _PublicTalkModel(
      title: 'Flutter не нужен, нужен натив!',
      talkUrl: '',
      tumbnailUrl: 'assets/talks_tumbnails/yet_di.webp',
    ),
    _PublicTalkModel(
      title: 'Анализ энергопотребления',
      talkUrl: '',
      tumbnailUrl: 'assets/talks_tumbnails/yet_di.webp',
    ),
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'Наши технологии',
          style: TextStyle(
            fontSize: 24,
            color: Colors.black,
          ),
        ),
        backgroundColor: Colors.grey.withOpacity(0.2),
        surfaceTintColor: Colors.white,
        elevation: 5,
      ),
      body: Center(
        child: Padding(
          padding: EdgeInsets.symmetric(horizontal: 8.0),
          child: Column(
            children: [
              SizedBox(height: 20),
              Text(
                '''Приложение Яндекс Про написано с использованием технологии Flutter, это такой фреймворк, который позволяет создавать приложения для Android и iOS используя одну кодовую базу.
                ''',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w400,
                ),
              ),
              Text(
                'В старых версиях таксометра использовался Kotlin, и приложение существовало только для платформы Anroid, но с появлением Flutter эта ситуация изменилась.',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w400,
                ),
              ),
              const SizedBox(height: 15),
              DecoratedBox(
                decoration: BoxDecoration(
                  color: AppColors.grayCard,
                  borderRadius: BorderRadius.all(Radius.circular(8)),
                ),
                child: SizedBox(
                  width: double.infinity,
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      'Мы делимся экспертизой',
                      style: TextStyle(fontSize: 16.0),
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 15),
              CarouselSlider(
                options: CarouselOptions(
                  height: 200.0,
                  enableInfiniteScroll: false,
                ),
                items: talksTumbnailUrls.map((i) {
                  return Builder(
                    builder: (BuildContext context) {
                      return Container(
                        width: MediaQuery.of(context).size.width,
                        margin: EdgeInsets.symmetric(horizontal: 5.0),
                        decoration: BoxDecoration(
                          color: AppColors.grayCard,
                          borderRadius: BorderRadius.all(Radius.circular(8)),
                        ),
                        child: Stack(
                          children: [
                            if (i.tumbnailUrl.isNotEmpty)
                              Image.asset(i.tumbnailUrl),
                            Align(
                              alignment: Alignment.bottomCenter,
                              child: DecoratedBox(
                                decoration: BoxDecoration(
                                  color: Colors.black26,
                                ),
                                child: SizedBox(
                                  width: double.infinity,
                                  child: Padding(
                                    padding: const EdgeInsets.all(4.0),
                                    child: Text(
                                      i.title,
                                      style: TextStyle(
                                        fontSize: 14.0,
                                        color: Colors.white,
                                      ),
                                    ),
                                  ),
                                ),
                              ),
                            ),
                          ],
                        ),
                      );
                    },
                  );
                }).toList()
                  ..add(
                    Builder(
                      builder: (BuildContext context) {
                        return Container(
                          width: MediaQuery.of(context).size.width,
                          margin: EdgeInsets.symmetric(horizontal: 5.0),
                          decoration: BoxDecoration(
                            color: AppColors.grayCard,
                            borderRadius: BorderRadius.all(Radius.circular(8)),
                          ),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.spaceAround,
                            children: [
                              Text(
                                'Больше докладов',
                                style: TextStyle(
                                  color: AppColors.black,
                                  fontSize: 32,
                                ),
                              ),
                              Padding(
                                padding:
                                    const EdgeInsets.symmetric(horizontal: 8.0),
                                child: PrimaryButton(
                                  text: 'Открыть',
                                  onTap: () {},
                                ),
                              ),
                            ],
                          ),
                        );
                      },
                    ),
                  ),
              ),
              const SizedBox(height: 15),
              DecoratedBox(
                decoration: BoxDecoration(
                  color: AppColors.grayCard,
                  borderRadius: BorderRadius.all(Radius.circular(8)),
                ),
                child: SizedBox(
                  width: double.infinity,
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      'Образование',
                      style: TextStyle(fontSize: 16.0),
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 15),
              SingleChildScrollView(
                scrollDirection: Axis.horizontal,
                child: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    const SizedBox(width: 15),
                    MainCard(
                      text: 'Хэндбук по Flutter',
                      onTap: () {},
                    ),
                    const SizedBox(width: 15),
                    MainCard(
                      text: 'Школа мобильной разработки',
                      onTap: () {},
                    ),
                    const SizedBox(width: 15),
                    MainCard(
                      text: 'Лекции в вузах',
                      onTap: () {},
                    ),
                    const SizedBox(width: 15),
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}

class _PublicTalkModel {
  final String title;
  final String tumbnailUrl;
  final String talkUrl;

  const _PublicTalkModel({
    required this.title,
    required this.tumbnailUrl,
    required this.talkUrl,
  });
}
