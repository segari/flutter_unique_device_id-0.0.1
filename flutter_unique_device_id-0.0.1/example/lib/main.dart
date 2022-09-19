import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:unique_device_id/flutter_unique_device_id.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _uniqueDeviceId = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String uniqueDeviceId;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      uniqueDeviceId = await UniqueDeviceId.get;
    } on PlatformException {
      uniqueDeviceId = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _uniqueDeviceId = uniqueDeviceId;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('uniqueDeviceId: $_uniqueDeviceId\n'),
        ),
      ),
    );
  }
}
