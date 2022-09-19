
import 'dart:async';

import 'package:flutter/services.dart';

class UniqueDeviceId {
  static const MethodChannel _channel =
      const MethodChannel('unique_device_id');

  static Future<String> get get async {
    final String uniqueDeviceId = await _channel.invokeMethod('getUniqueDeviceId');
    return uniqueDeviceId;
  }
}
