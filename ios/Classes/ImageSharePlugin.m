#import "ImageSharePlugin.h"
#import <image_share/image_share-Swift.h>

@implementation ImageSharePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftImageSharePlugin registerWithRegistrar:registrar];
}
@end
