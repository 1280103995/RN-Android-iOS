#import "RNRouteInfo.h"

@implementation RNRouteInfo

- (void)setRouteName:(NSString*)name {
  routeName = name;
}

- (void)setRouteParams:(NSDictionary*)params {
  routeParams = params;
}

/**
 {
   "NativeRouteInfo": {
      "routeName": "One",
      "routeParams": {
         "initTitle": "iOS标题"
      }
    }
 }
 */
- (NSDictionary *)toNSDictionary{
  NSDictionary *dic = @{@"NativeRouteInfo":@{
                            @"routeName":routeName,
                            @"routeParams": routeParams
                          }
                       };
 return dic;
}

@end
