/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import "AppDelegate.h"

#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>
#import "ViewController.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
  
  UINavigationController *rootViewController = [[UINavigationController alloc] initWithRootViewController:[[ViewController alloc] init]];
  //隐藏导航栏
  [rootViewController setNavigationBarHidden:YES animated:YES];
  self.nav = rootViewController;
  self.window.rootViewController = rootViewController;
  //视图可见
  [self.window makeKeyAndVisible];
  return YES;
}

@end

