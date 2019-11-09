#import "CommonModule.h"
#import "AppDelegate.h"
#import "NativeViewController.h"

@implementation CommonModule

//导出模块
RCT_EXPORT_MODULE();    //此处不添加参数即默认为这个OC类的名字

//返回上一个控制器
RCT_EXPORT_METHOD(finish){

  dispatch_async(dispatch_get_main_queue(), ^{
    AppDelegate *app = (AppDelegate *)[[UIApplication sharedApplication] delegate];
    [app.nav popViewControllerAnimated:YES];
  });
  
}

//打开原生页面
RCT_EXPORT_METHOD(openNativeScreen){

  dispatch_async(dispatch_get_main_queue(), ^{
    NativeViewController *nativeVC = [[NativeViewController alloc] init];
    
    AppDelegate *app = (AppDelegate *)[[UIApplication sharedApplication] delegate];
    [app.nav pushViewController:nativeVC animated:YES];
  });

}

@end
