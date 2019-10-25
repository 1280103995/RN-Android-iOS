#import "RNMainViewController.h"
#import "ViewController.h"
#import "RNView.h"
#import "RNRouteInfo.h"

@interface RNMainViewController ()

@end

@implementation RNMainViewController

- (void)viewDidLoad {
  [super viewDidLoad];
  self.title = @"RN控制器";

  //初始化RN路由信息
  RNRouteInfo *info = [[RNRouteInfo alloc] init];
  //设置要进入的RN页面
  [info setRouteName:@"One"];
  //设置要传入的参数
  NSDictionary * params = [NSDictionary dictionary];
  [params setValue:@"iOS标题" forKey:@"initTitle"];
  [info setRouteParams:params];
  
  NSString * route = [info toString:info];
  
  //route
  NSDictionary * rnRouteInfo = @{@"NativeRouteInfo":route};
  
  self.view = [RNView loadReactNativeView: rnRouteInfo];
}
@end
