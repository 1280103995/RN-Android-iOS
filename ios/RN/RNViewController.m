#import "ViewController.h"
#import "RNViewController.h"
#import "RNView.h"

@interface RNViewController ()

@end

@implementation RNViewController

- (void)viewDidLoad {
  [super viewDidLoad];

  self.view = [RNView loadReactNativeView: self.rnRouteInfo];

}
@end
