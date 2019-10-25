#import "ViewController.h"
#import "RNMainViewController.h"
#import "RNView.h"
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
  RNMainViewController *vc = [[RNMainViewController alloc] init];
  [self.navigationController pushViewController:vc animated:YES];
}

@end
