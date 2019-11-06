#import <Foundation/Foundation.h>

@interface RNRouteInfo : NSDictionary {
  NSString* routeName;
  NSDictionary* routeParams;
}

//@property(nonatomic,retain) NSString * routeName;
//@property(nonatomic,retain) NSDictionary * routeParams;

- (void)setRouteName:(NSString*)name;
- (void)setRouteParams:(NSDictionary*)params;
- (NSDictionary*)toNSDictionary;

@end
