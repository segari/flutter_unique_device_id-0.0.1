package com.sharequeue.unique_device_id;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import java.util.UUID;


/** UniqueDeviceIdPlugin */
public class UniqueDeviceIdPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private FlutterPluginBinding _flutterPluginBinding;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "unique_device_id");
    channel.setMethodCallHandler(this);
    _flutterPluginBinding = flutterPluginBinding;
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getUniqueDeviceId")) {
        Log.d("================", "onMethodCall: ");
//        final TelephonyManager tm = (TelephonyManager) _flutterPluginBinding.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//
//        final String tmDevice, tmSerial, androidId;
//        tmDevice = "" + tm.getDeviceId();
//        tmSerial = "" + tm.getSimSerialNumber();
//        androidId = "" + android.provider.Settings.Secure.getString(_flutterPluginBinding.getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//
//        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = getUniquePsuedoID();
      result.success(deviceId);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    _flutterPluginBinding = null;
  }

    /**
     * Return pseudo unique ID
     * @return ID
     */
    private String getUniquePsuedoID() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // https://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = android.os.Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // String needs to be initialized
            final String androidId = "" + android.provider.Settings.Secure.getString(_flutterPluginBinding.getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            serial = androidId;
        }

        // if Build.SERIAL get successfuly, the 'id' is unique very likely, if not, we use androidId to guarante 'id' to be unique as possible
        // without any permissions


        // Thanks @Joe!
        // https://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}
