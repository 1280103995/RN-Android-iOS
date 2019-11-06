#import "ViewController.h"
#import "RNViewController.h"
#import "RNRouteInfo.h"

@interface ViewController ()

@end

@implementation ViewController



- (void)viewDidLoad {
  [super viewDidLoad];
  
  self.title = @"这是iOS原生页面";
  self.view.backgroundColor = [UIColor brownColor];
  
  UIButton *button = [[UIButton alloc] initWithFrame:CGRectMake(100, 100, 200, 50)];
  [button setTitle:@"点击跳转到RN页面" forState:UIControlStateNormal];
  [button addTarget:self action:@selector(onClickButton) forControlEvents:UIControlEventTouchUpInside];
  
  [self.view addSubview:button];
  
}


- (void)onClickButton {

  RNViewController *vc = [[RNViewController alloc] init];
  //初始化RN路由信息
  RNRouteInfo *info = [[RNRouteInfo alloc] init];
  //设置要进入的RN页面
  [info setRouteName:@"Home"];
  //设置要传入的参数
  NSDictionary * params = @{@"initTitle": @"iOS标题"};
  [info setRouteParams:params];
  vc.rnRouteInfo = info.toNSDictionary;
  
  [self presentModalViewController:vc animated:YES];
}

@end
