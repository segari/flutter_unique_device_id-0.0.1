#import "UniqueDeviceIdPlugin.h"
#import "UIDevice+FCUUID.h"

@implementation UniqueDeviceIdPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"unique_device_id"
            binaryMessenger:[registrar messenger]];
  UniqueDeviceIdPlugin* instance = [[UniqueDeviceIdPlugin alloc] init];
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
