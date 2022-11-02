
import 'dart:async';

import 'package:flutter/services.dart';

class FlutterUniqueDeviceId {
  static const MethodChannel _channel = MethodChannel('flutter_unique_device_id');

  static Future<String?> get get async {
    final String? uniqueDeviceId = await _channel.invokeMethod('getUniqueDeviceId');
    return uniqueDeviceId;
  }
}
