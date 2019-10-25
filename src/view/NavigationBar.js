import * as React from 'react';
import {Platform, StyleSheet, View} from "react-native";
import {getStatusBarHeight, px2dp, screenW, wh} from "../utils/ScreenUtil";
import Row from "./Row";
import Color from "../app/Color";

type Props = {
  leftView: Function,
  centerView: Function,//如果不想要原来标题位置的样式，自定义一个View进来替换
  rightView: Function
};

export default class NavigationBar extends React.Component<Props> {
  //Android版本大于等于4.4，才有状态栏
  _showStatusBar = Platform.OS === 'android' && Platform.Version >= 19 || Platform.OS === 'ios';

  static defaultProps = {
    leftView: ()=>null,
    centerView: ()=>null,
    rightView: ()=>null
  };

  render() {
    return (
      <View style={styles.navigationBarStyle}>
          <View style={styles.statusBarStyle}/>

        <Row verticalCenter horizontalCenter style={styles.barStyle}>
          {/*导航栏左边组件*/}
          <View style={{position: 'absolute', left: 0}}>
            {this.props.leftView()}
          </View>

          {/*导航栏中间组件*/}
          {this.props.centerView()}

          {/*导航栏右边组件*/}
          <View style={{position: 'absolute', right: 0}}>
            {this.props.rightView()}
          </View>

        </Row>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  navigationBarStyle: {
    backgroundColor: Color.white,
    borderBottomColor: Color.divider,
    borderBottomWidth: px2dp(1),
    // elevation: 2,
    // shadowOffset: {width: 0, height: 3},
    // shadowOpacity: 0.5,
    // shadowRadius: 3,
    // shadowColor: Color.gray,
    // backgroundColor: Color.white
  },
  statusBarStyle: {
    height: getStatusBarHeight()
  },
  barStyle: {
    height: px2dp(44)
  }
});