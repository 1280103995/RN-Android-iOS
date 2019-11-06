#import "RNView.h"
#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>
@implementation RNView
 
+ (UIView *)loadReactNativeView:(NSDictionary*)params
{
  
    NSURL *jsCodeLocation;
  
    #ifdef DEBUG
      jsCodeLocation = [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index" fallbackResource:nil];
    #else
      jsCodeLocation = [[NSBundle mainBundle] URLForResource:@"main" withExtension:@"jsbundle"];
    #endif

    RCTRootView  *rootView =[[RCTRootView alloc] initWithBundleURL:jsCodeLocation
                                                        moduleName:@"RN"
                                                 initialProperties:params
                                                     launchOptions:nil];
    return rootView;
}
 
 
@end
