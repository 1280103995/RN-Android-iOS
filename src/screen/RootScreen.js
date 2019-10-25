import React, {Component} from 'react';
import NavigationUtil from "../utils/NavigationUtil";
import {NavigationParams} from "react-navigation";

export default class RootScreen extends Component {

  constructor(props) {
    super(props);
    const RouteName = RouteInfo.routeName;
    const RouteParams = RouteInfo.routeParams;
    this._push(RouteName, RouteParams);
  }

  /**
   * 通过重置路由方式实现初始化不同的页面
   * @param routeName 在StackNavigator中注册的页面
   * @param params 如 {user_id: 21, money: 100}
   * @private
   */
  _push = (routeName: string, params?: NavigationParams) => {
    NavigationUtil.reset(this.props.navigation, routeName, params)
  };

  render() {
    return null;
  }
}