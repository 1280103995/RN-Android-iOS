import React from 'react';
import {
  StyleSheet,
  Text,
  Image,
  View,
  Button
} from 'react-native';
import Images from "../res/Images";
import BaseScreen from "./BaseScreen";

export default class TestTwoScreen extends BaseScreen {

  componentWillMount() {
    this.setTitle('RN二级页面');
  }

  renderView() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome} >React Native</Text>
        <Image source={Images.Main.img2} style={{resizeMode: 'center'}}/>
          <Button title='进入RN三级页面' onPress={this._Click}/>

      </View>
    );
  }

  _Click = () => {
    this.props.navigation.navigate('TestThree')
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
