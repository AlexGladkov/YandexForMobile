import 'package:flutter/material.dart';

import 'colors.dart';

class PrimaryButton extends StatelessWidget {
  final VoidCallback onTap;
  final String text;

  const PrimaryButton({
    required this.text,
    required this.onTap,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return MaterialButton(
      onPressed: onTap,
      height: 70,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(16),
      ),
      minWidth: double.infinity,
      elevation: 0,
      color: AppColors.primary,
      child: Text(
        text,
        style: TextStyle(
          fontSize: 22,
          fontWeight: FontWeight.w600,
        ),
      ),
    );
  }
}
