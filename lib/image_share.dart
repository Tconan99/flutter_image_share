import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/services.dart';

class ImageShare {
  static const MethodChannel _channel = const MethodChannel('image_share');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future save(String path, Uint8List imageBytes) async {
    assert(imageBytes != null);
    final result = await _channel.invokeMethod('shareImageToGallery', <String, dynamic>{
      "path": path,
      "image": imageBytes
    });
    return result;
  }
}
