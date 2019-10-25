import React from 'react';
import {
  View,
  StyleSheet,
  PanResponder,
  Dimensions
} from 'react-native';
import Color from "../app/Color";

const screenWidth = Dimensions.get('window').width;
const screenHeight = Dimensions.get('window').height;

let _previousLeft = 0;
let _previousTop = 0;

let lastLeft = 0;
let lastTop = 0;

const CIRCLE_SIZE = 80;

let targe = null;
let limitTop = 0;
let limitBottom = 0;

export default class FloatingBtn extends React.Component {

  constructor(props) {
    super(props);

  }

  componentWillMount(evt, gestureState) {

    this._panResponder = PanResponder.create({
      onStartShouldSetPanResponder: (evt, gestureState)=>{
          return true
      },
      onMoveShouldSetPanResponder: (evt, gestureState)=>{
        return true
      },
      onPanResponderGrant: ()=>{
        this._setBallPosition(_previousLeft, _previousTop)
      },

      //最近一次的移动距离.如:(获取x轴y轴方向的移动距离 gestureState.dx,gestureState.dy)
      onPanResponderMove: (evt, gestureState)=>{
        _previousLeft = lastLeft + gestureState.dx;
        _previousTop = lastTop + gestureState.dy;

        //主要是限制小球拖拽移动的时候不许出屏幕外部
        if (_previousLeft <= 0) {
          _previousLeft = 0;
        }
        if (_previousTop <= 0) {
          _previousTop = 0;
        }

        if (_previousLeft >= screenWidth - CIRCLE_SIZE) {
          _previousLeft = screenWidth - CIRCLE_SIZE;
        }
        if (_previousTop >= screenHeight - CIRCLE_SIZE) {
          _previousTop = screenHeight - CIRCLE_SIZE;
        }

        this._setBallPosition(_previousLeft, _previousTop)
      },

      onPanResponderRelease: ()=>{
        lastLeft = _previousLeft;
        lastTop = _previousTop;

        this._changePosition();
      }
    });
  }

  _changePosition() {
    if (_previousLeft + CIRCLE_SIZE / 2 <= screenWidth / 2) {
      _previousLeft = lastLeft = 0;
    } else {
      _previousLeft = lastLeft = screenWidth - CIRCLE_SIZE;
    }
    this._setBallPosition(_previousLeft, _previousTop)
  }

  _setBallPosition(left, top){
    this.ball.setNativeProps({
      style: {left, top}, //_previousLeft和_previousTop是两个变量，用来记录小球移动坐标
    })
  }

  render() {
    return (
      <View
        ref={(r)=>this.ball=r}
        style={styles.circle}
        {...this._panResponder.panHandlers}/>
    );
  }

  async setTarge(view){

  }

}
const styles = StyleSheet.create({
  circle: {
    width: CIRCLE_SIZE,
    height: CIRCLE_SIZE,
    borderRadius: CIRCLE_SIZE / 2,
    backgroundColor: 'green',
    position: 'absolute',
    elevation:6,
    shadowColor:Color.gray,
    shadowOffset:{width:1,height:1},
    shadowOpacity:0.5,
    shadowRadius:1
  }
});