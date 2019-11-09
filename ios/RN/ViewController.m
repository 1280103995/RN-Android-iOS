#import "ViewController.h"
#import "RNViewController.h"
#import "RNRouteInfo.h"
#import "AppDelegate.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
  [super viewDidLoad];

  self.view.backgroundColor = [UIColor brownColor];
  
  UIButton *button = [[UIButton alloc] initWithFrame:CGRectMake(100, 230, 200, 50)];
  [button setTitle:@"进入RN页面" forState:UIControlStateNormal];
  [button addTarget:self action:@selector(onClickButton) forControlEvents:UIControlEventTouchUpInside];
  
  [self.view addSubview:button];
  
}

- (void)onClickButton {

  RNViewController *vc = [[RNViewController alloc] init];
  //初始化RN路由信息
  RNRouteInfo *info = [[RNRouteInfo alloc] init];
  //设置要进入的RN页面
  [info setRouteName:@"TestOne"];
  //设置要传入的参数
  NSDictionary * params = @{@"initTitle": @"从iOS首页过来"};
  [info setRouteParams:params];
  vc.rnRouteInfo = info.toNSDictionary;
  
  AppDelegate *app = (AppDelegate *)[[UIApplication sharedApplication] delegate];
  [app.nav pushViewController:vc animated:YES];
}

@end
