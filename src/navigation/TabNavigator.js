import React from 'react'
import { createBottomTabNavigator } from 'react-navigation-tabs';
import TabOneScreen from "../screen/TabOneScreen";
import TabTwoScreen from "../screen/TabTwoScreen";
import TabThreeScreen from "../screen/TabThreeScreen";
import TabBar from "../view/TabBar";

export const Tabs = createBottomTabNavigator(
  {
    TabOne: {
      screen: TabOneScreen,
      navigationOptions: ({navigation}) => ({
        tabBarLabel: '第一页'
      }),
    },
    TabTwo: {
      screen: TabTwoScreen,
      navigationOptions: ({navigation}) => ({
        tabBarLabel: '第二页'
      }),
    },
    TabThree: {
      screen: TabThreeScreen,
      navigationOptions: ({navigation}) => ({
        tabBarLabel: '第三页'
      })
    },
  },
  {
    backBehavior: 'none', // 按 back 键是否跳转到第一个 Tab， none 为不跳转，默认为 initialRoute 的行为。
    lazy: true,//是否根据需要懒惰呈现标签，而不是提前制作，意思是在app打开的时候将底部标签栏全部加载，默认false
    tabBarComponent: (props) => <TabBar {...props}/>,
    tabBarOptions: {
      activeTintColor: '#00ff0f', // 文字和图片选中颜色
      activeBackgroundColor: 'white',
      inactiveTintColor: '#979797', // 文字和图片默认颜色
      inactiveBackgroundColor: 'white',
      showLabel: true, // 显示标签
      showIcon: true, // 显示icon
      style: {
        backgroundColor: '#fff', // TabBar 背景色
      },
      labelStyle: {
        fontSize: 12, // 文字大小
      },
      tabStyle: {},
      allowFontScaling: true,
      safeAreaInset: {}
    },
    navigationOptions: null
  }
);
