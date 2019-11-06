'use strict';
import React, {Component} from 'react';
import GLOBAL from './src/contants/Global';
import {AppContainer} from "./src/navigation/AppNavigator";

global.isLogin = false;
global.tokenTime = '0';
export default class App extends Component {

  constructor(props) {
    super(props);
    if(this.props.NativeRouteInfo){
      if (typeof this.props.NativeRouteInfo === 'object'){//ios
        global.RouteInfo = this.props.NativeRouteInfo
      }else {//android
        global.RouteInfo = JSON.parse(this.props.NativeRouteInfo);
      }
    }
  }

  render() {
    return (
        <AppContainer/>
    );
  }
}

