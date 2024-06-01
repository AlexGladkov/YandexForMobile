import 'package:flutter/material.dart';

class TeamPage extends StatefulWidget {
  const TeamPage({super.key});

  @override
  State<TeamPage> createState() => _TeamPageState();
}

class _TeamPageState extends State<TeamPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Text(
          '''
Team page
To be done
          ''',
          style: TextStyle(
            fontSize: 46,
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
    );
  }
}
