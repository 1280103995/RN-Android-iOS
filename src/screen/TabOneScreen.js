/**
 * Created by panda on 2017/1/18.
 */
import React, {Component} from 'react';
import {
  View,
  StatusBar,
  Text,
} from 'react-native';

export default class TabOneScreen extends Component {

  static navigationOptions = {
    title: '主界面Tab1',//设置标题内容
    // header: null,
    gesturesEnabled: true
  };

  render() {
    return (
      <View style={{flex: 1, backgroundColor: '#FFFFFF'}}>
        <StatusBar backgroundColor={'red'}/>
        <Text>主界面Tab1</Text>
      </View>
    );
  };

}
