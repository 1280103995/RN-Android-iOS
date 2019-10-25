import React from 'react';
import {StatusBar, Text,View} from "react-native";
export default class TabTwoScreen extends React.Component{

  render(){
    return(
      <View>
        <StatusBar backgroundColor={'green'}/>
      <Text>{'第二个Tab'}</Text>
      </View>
    )
  }
}