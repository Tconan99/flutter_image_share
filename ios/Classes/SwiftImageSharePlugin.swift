import Flutter
import UIKit

public class SwiftImageSharePlugin: NSObject, FlutterPlugin {
  var result: FlutterResult?;

  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "image_share", binaryMessenger: registrar.messenger())
    let instance = SwiftImageSharePlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if call.method = "getPlatformVersion" {
      result("iOS " + UIDevice.current.systemVersion)
    } else if call.method = "saveImageToGallery" {
      if let arguments = (call.arguments as? FlutterStandardTypedData)?.data, let image = UIImage(data: arguments) {
        UIImageWriteToSavedPhotosAlbum(image, self, #selector(didFinishSaving(image:error:contextInfo:)), nil)
      }
    } else {
      result(FlutterMethodNotImplemented)
    }

  }

  /// finish saving，if has error，parameters error will not nill
  @objc func didFinishSaving(image: UIImage, error: NSError?, contextInfo: UnsafeMutableRawPointer?) {
    result?(error == nil)
  }
}
