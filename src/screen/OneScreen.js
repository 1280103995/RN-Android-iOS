import React from "react";
import {Image, ScrollView, StyleSheet, Text} from "react-native";
import Swiper from 'react-native-swiper';

export default class OneScreen extends React.Component {
  static navigationOptions = ({ navigation }) => {
    return {
      title: navigation.getParam('initTitle', '拿不到参数'),
      gesturesEnabled: true
    };
  };

  render() {
    return (
      <ScrollView style={{flex:1,backgroundColor:'white'}}>
        {this._renderBanner()}
      </ScrollView>
    );
  }

  _renderBanner() {
      const img = ['https://ws1.sinaimg.cn/large/610dc034ly1fp9qm6nv50j20u00miacg.jpg',
                    'https://ws1.sinaimg.cn/large/610dc034ly1foowtrkpvkj20sg0izdkx.jpg',
                    'http://7xi8d6.com1.z0.glb.clouddn.com/20180208080314_FhzuAJ_Screenshot.jpeg'];
    return (
      <Swiper  height={200} paginationStyle={{bottom: 10}}>
        <Image source={{uri:img[0]}} style={[styles.banner,{resizeMode:'stretch'}]}/>
        <Image source={{uri:img[1]}} style={styles.banner}/>
        <Image source={{uri:img[2]}} style={styles.banner}/>
      </Swiper>
    )
  }

}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
  },
  bg:{
    backgroundColor:'white'
  },
  banner: {
    width: '100%',
    height: 200,
    backgroundColor:'green'
  }
});