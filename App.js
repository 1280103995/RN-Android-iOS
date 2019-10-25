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
      global.RouteInfo = JSON.parse(this.props.NativeRouteInfo);
    }
  }

  render() {
    return (
        <AppContainer/>
    );
  }
}

