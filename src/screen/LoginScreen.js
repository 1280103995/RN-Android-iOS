import React from 'react';
import {Button, Text, TextInput, View} from "react-native";
import BaseScreen from "./BaseScreen";

export default class LoginScreen extends BaseScreen {

  constructor(props) {
    super(props);
    this.setTitle("登录");
    this.state = {
      mobile: 'ly001',
      pwd: '123456'
    };
  }

  _login = () => {

  };

  renderView() {
    return (
      <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
        <View style={{flexDirection: 'row', alignItems: 'center',marginVertical: 10}}>
          <Text>账号</Text>
          <TextInput
            style={{width:200}}
            value={this.state.mobile}
            onChangeText={(text) => {
              this.setState({
                mobile:text
              })
            }}
          />
        </View>
        <View style={{flexDirection: 'row', marginVertical: 10}}>
          <Text>密码</Text>
          <TextInput
            style={{width:200}}
            value={this.state.pwd}
            onChangeText={(text) => {
              this.setState({
                pwd:text
              })
            }}
          />
        </View>
        <Button title='登陆' onPress={this._login}/>
      </View>
    )
  }
}