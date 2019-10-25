import React from 'react';
import {BackHandler, NativeModules, StatusBar, Text, TouchableOpacity, View} from "react-native";
import NavigationBar from "../view/NavigationBar";
const CommModule = NativeModules.CommModule;

export default class BaseScreen extends React.Component {
  navBarVisible: boolean = true;
  title: string = '';

  static navigationOptions = {
    header: null,
    gesturesEnabled:false
  };

  render() {
    return (
      <View style={{flex: 1}} onTouchEnd={(event) => this._onTouchEnd(event)}>
        <StatusBar
          animated={false}
          hidden={false}
          backgroundColor={'transparent'}
          translucent={true}
          barStyle={'dark-content'}/>

          <NavigationBar
            leftView={this.renderNavLeftView}
            centerView={this.renderNavCenterView}
            rightView={this.renderNavRightView}
          />


        {this.renderView()}

        {/* 悬浮按钮 */}
        {/*<FloatingBtn*/}
          {/*ref={(f) => this.fb = f}*/}
          {/*navigation={this.props.navigation}*/}
          {/*defaultPosition={{x: 2, y: (screenW - 50)}}/>*/}
      </View>
    )
  }

  renderNavLeftView = () => {
    return (
      <TouchableOpacity activeOpacity={1} onPress={this.navLeftClick}>
        <Text>返回</Text>
      </TouchableOpacity>
    )
  };

  navLeftClick = () => {
    if (!this.props.navigation.goBack()){
      CommModule.finish()
    } else {
      this.props.navigation.goBack()
    }
  };

  renderNavCenterView = () => {
    return(
      <Text largeSize style={{backgroundColor: 'transparent'}}>{this.title}</Text>
    )
  };

  renderNavRightView = () => {
    return null
  };

  renderView() {
    return null
  }

  _onTouchEnd(event) {
    // this.fb.onTouchEnd(event)
  }

  setNavBarVisible(visible: boolean){
    this.navBarVisible = visible
  }

  setTitle(title: string){
    this.title = title
  }
}