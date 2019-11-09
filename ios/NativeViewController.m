#import "NativeViewController.h"
#import "RNViewController.h"
#import "RNRouteInfo.h"
#import "AppDelegate.h"

@interface NativeViewController ()

@end

@implementation NativeViewController

- (void)viewDidLoad {
  [super viewDidLoad];

  self.view.backgroundColor = [UIColor purpleColor];
  
  UIButton *btnBack = [[UIButton alloc] initWithFrame:CGRectMake(1, 30, 50, 50)];
  [btnBack setTitle:@"返回" forState:UIControlStateNormal];
  [btnBack addTarget:self action:@selector(onBackClick) forControlEvents:UIControlEventTouchUpInside];
  [self.view addSubview:btnBack];
  
  UIButton *button = [[UIButton alloc] initWithFrame:CGRectMake(100, 200, 200, 50)];
  [button setTitle:@"再次进入RN页面" forState:UIControlStateNormal];
  [button addTarget:self action:@selector(onClickButton) forControlEvents:UIControlEventTouchUpInside];
  [self.view addSubview:button];
}

- (void)onClickButton {
  
  RNViewController *vc = [[RNViewController alloc] init];
  //初始化RN路由信息
  RNRouteInfo *info = [[RNRouteInfo alloc] init];
  //设置要进入的RN页面
  [info setRouteName:@"TestThree"];

  vc.rnRouteInfo = info.toNSDictionary;
  
  AppDelegate *app = (AppDelegate *)[[UIApplication sharedApplication] delegate];
  [app.nav pushViewController:vc animated:YES];
}

- (void)onBackClick{
  AppDelegate *app = (AppDelegate *)[[UIApplication sharedApplication] delegate];
  [app.nav popViewControllerAnimated:YES];
}
@end
