package com.tconan.imageshare

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageSharePlugin(private val registrar: Registrar): MethodCallHandler {
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "image_share")
      channel.setMethodCallHandler(ImageSharePlugin(registrar))
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "shareImageToGallery") {
      // val path = call.argument("path") as String
      // val image = call.argument("image") as ByteArray
      var image = call.arguments as ByteArray
      result.success(shareImageToGallery("", BitmapFactory.decodeByteArray(image, 0, image.size)))
    } else {
      result.notImplemented()
    }
  }

  private fun shareImageToGallery(path: String, bmp: Bitmap): Boolean {
    val context = registrar.activeContext().applicationContext
    val storePath =  Environment.getExternalStorageDirectory().absolutePath + File.separator + "mr_grid"

    // val file = File(path)
    val appDir = File(storePath)

    if (!appDir.exists()) {
      appDir.mkdir()
    }

     val fileName = System.currentTimeMillis().toString() + ".png"
     val file = File(appDir, fileName)
    if (!file.exists()) {
      file.createNewFile()
    }
    try {
      val fos = FileOutputStream(file)
      val isSuccess = bmp.compress(Bitmap.CompressFormat.PNG, 60, fos)
      fos.flush()
      fos.close()
      val uri = Uri.fromFile(file)
      context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
      return isSuccess
    } catch (e: IOException) {
      e.printStackTrace()
    }
    return false
  }
}
