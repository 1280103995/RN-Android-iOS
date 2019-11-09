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

export default class TestOneScreen extends BaseScreen {

  componentWillMount() {
    const title = this.props.navigation.getParam('initTitle', '取不到原生传过来的标题');
    this.setTitle(title);
  }

  renderView() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome} >React Native</Text>

          <Image source={Images.Main.img1} style={{resizeMode: 'center'}}/>

        <View style={{flexDirection:'row'}}>
          <Button title='进入RN二级页面' onPress={this._Click}/>
        </View>
      </View>
    );
  }

  _Click = () => {
    this.props.navigation.navigate('TestTwo')
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
