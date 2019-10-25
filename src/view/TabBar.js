/**
 * 中间icon凸出组件
 */

import React, {Component} from "react";
import {
  View,
  Text,
  StyleSheet,
  Dimensions,
  TouchableOpacity,
} from "react-native";

const screenW = Dimensions.get('window').width;

export default class TabBar extends Component {

  tabBarHeight: number = 49; //tabBar高度
  itemWidth: number; //tab item 宽度

  static defaultProps = {};

  renderItem = (route, index, count) => {
    const {navigation, jumpTo} = this.props;
    const focused = index === navigation.state.index;
    const color = focused ? this.props.activeTintColor : this.props.inactiveTintColor;
    let TabScene = {
      focused: focused,
      route: route,
      tintColor: color
    };

    if (index === Math.floor(count / 2)) {
      return <View key={"" + index} style={{width: this.itemWidth}}/>//占位使用
    }

    return (
      <TouchableOpacity
        key={route.key}
        onPress={() => jumpTo(route.key)}>
        <View style={[styles.tabItem, {width: this.itemWidth, height: this.tabBarHeight}]}>
          {this.props.renderIcon(TabScene)}
          <Text style={{...this.props.labelStyle, color: color}}>{this.props.getLabelText(TabScene)}</Text>
        </View>
      </TouchableOpacity>
    );
  };


  renderCenter = (route, index, count) => {
    const {navigation, jumpTo} = this.props;
    const focused = index === navigation.state.index;
    const color = focused ? this.props.activeTintColor : this.props.inactiveTintColor;
    let TabScene = {
      focused: focused,
      route: route,
      tintColor: color
    };
    return (
      <TouchableOpacity
        key={"centerView"}
        style={{
          position: 'absolute',
          bottom: 15,
          left: screenW / 2 - 25, //凸出item居中：屏幕宽度的一半减去凸出item宽度的一半
          backgroundColor: 'transparent',
        }}
        onPress={() => jumpTo(route.key)}>
        <View style={{
          width: 50,
          height: 50,
          borderRadius: 25,
          alignItems: 'center',
          justifyContent: 'center',
          backgroundColor: 'gray',
        }}>
          {this.props.renderIcon(TabScene)}
        </View>
      </TouchableOpacity>
    )
  };

  render() {
    const {navigation,} = this.props;
    const {routes} = navigation.state;
    console.log('Tab', this.props);

    this.itemWidth = screenW / routes.length;

    let normalItem = [];
    let centerItem;
    for (let i = 0; i < routes.length; i++) {
      normalItem.push(this.renderItem(routes[i], i, routes.length)); //其他正常item
      if (i === Math.floor(routes.length / 2)) {//中间凸起的item
        centerItem = this.renderCenter(routes[i], i, routes.length)
      }
    }

    return (
      <View
        pointerEvents={"box-none"}//此组件不接收点击事件 子组件可以点击
        style={{width: screenW, justifyContent: 'flex-end'}} //添加其他style会失效！！！
      >
        {/*其他正常View*/}
        <View
          style={{width: screenW, flexDirection: 'row', alignItems: 'center', backgroundColor: 'white'}}>
          {normalItem}
        </View>
        {/*中间凸起的view*/}
        {centerItem}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  tabItem: {
    alignItems: 'center',
    justifyContent: 'center'
  }
});


