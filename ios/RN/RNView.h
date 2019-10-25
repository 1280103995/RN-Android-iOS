#import <UIKit/UIKit.h>
 //RNView 类名
@interface RNView : NSObject 
 
/**
 用于加载React Native
 
 @return 返回Native 界面
 */
+ (UIView *)loadReactNativeView:(NSDictionary*)name;
 
@end
