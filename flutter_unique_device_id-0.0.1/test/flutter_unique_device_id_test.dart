import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_unique_device_id/flutter_unique_device_id.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_unique_device_id');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '423dfg-3434-234234-234';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await FlutterUniqueDeviceId.get, '423dfg-3434-234234-234');
  });
}
