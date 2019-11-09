import React from 'react';
import {
  StyleSheet,
  Text,
  Image,
  View,
  Button, NativeModules,
} from 'react-native';
import BaseScreen from "./BaseScreen";
import Images from '../res/Images';
const CommonModule = NativeModules.CommonModule;

export default class TestThreeScreen extends BaseScreen {

  componentWillMount() {
    this.setTitle('RN三级页面');
  }

  renderView() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome} >React Native</Text>
        <Image source={Images.Main.img3} style={{resizeMode: 'center'}}/>
          <Button title='进入原生页面' onPress={this._Click}/>

      </View>
    );
  }

  _Click = () => {
    CommonModule.openNativeScreen()
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
