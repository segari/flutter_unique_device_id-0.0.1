#import "FlutterUniqueDeviceIdPlugin.h"
#import "UIDevice+FCUUID.h"

@implementation FlutterUniqueDeviceIdPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"flutter_unique_device_id"
            binaryMessenger:[registrar messenger]];
  FlutterUniqueDeviceIdPlugin* instance = [[FlutterUniqueDeviceIdPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getUniqueDeviceId" isEqualToString:call.method]) {
    result([[UIDevice currentDevice] uuid]);
  } else {
    result(FlutterMethodNotImplemented);
  }
}

@end
