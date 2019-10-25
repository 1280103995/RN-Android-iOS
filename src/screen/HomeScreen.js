'use strict';
import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  Image,
  View,
  Platform,
  NativeModules,
  ToastAndroid,
  DeviceEventEmitter, Button
} from 'react-native';
import Images from "../res/Images";
import BaseScreen from "./BaseScreen";
const CommModule = NativeModules.CommModule;

let title = 'React Native界面';

export default class HomeScreen extends BaseScreen {

  componentWillMount() {
    const title = this.props.navigation.getParam('initTitle', '拿不到标题');
    this.setTitle(title);
    if (Platform.OS === 'ios') return;
    let result = CommModule.Constant;
    console.log('原生端返回的常量值为：' + result);
  }

  /**
   * 接收原生调用
   */
  componentDidMount() {
    DeviceEventEmitter.addListener('nativeCallRn',(msg)=>{
      title = "React Native界面,收到数据：" + msg;
      ToastAndroid.show("发送成功", ToastAndroid.SHORT);
    })
  }

  /**
   * 调用原生代码
   */
  skipNativeCall() {
    let phone = '18637070949';
    CommModule.callPhone(phone);
  }

  /**
   * Callback 通信方式
   */
  callbackComm(msg) {
    CommModule.rnCallNativeFromCallback(msg,(result) => {
      ToastAndroid.show("CallBack收到消息:" + result, ToastAndroid.SHORT);
    })
  }

  /**
   * Promise 通信方式
   */
  promiseComm(msg) {
    CommModule.rnCallNativeFromPromise(msg).then(
      (result) =>{
        ToastAndroid.show("Promise收到消息:" + result, ToastAndroid.SHORT)
      }
    ).catch((error) =>{console.log(error)});
  }

  renderView() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome} >{title}</Text>
        <Text style={styles.welcome} onPress={this.skipNativeCall.bind(this)}>
          跳转到拨号界面
        </Text>
        <Text style={styles.welcome} onPress={this.callbackComm.bind(this,'callback发送啦')}>
          Callback通信方式
        </Text>
        <Text style={styles.welcome} onPress={this.promiseComm.bind(this,'promise发送啦')}>
          Promise通信方式
        </Text>
        <View style={{flexDirection:'row'}}>
          <Image source={Images.Main.img1} />
          <Image source={Images.Main.img2} />
          <Image source={Images.Main.img3} />
        </View>
        <View style={{flexDirection:'row'}}>
          <Button title='第一页' onPress={()=>this.props.navigation.navigate('Login')}/>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  }
});
