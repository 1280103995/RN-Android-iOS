import React, {PureComponent} from 'react';
import {
  Text,
  View,
  Animated,
  Easing,
  StyleSheet,
  ViewPropTypes,
} from 'react-native';
import PropTypes from 'prop-types';

export default class NoticeBar extends PureComponent {

  static propTypes = {
    onChange: PropTypes.func,
    animationEnable: PropTypes.bool,
    data: PropTypes.array.isRequired,
    delay: PropTypes.number, // 每一次滚动切换之前延迟的时间（单位是毫秒），默认为500
    duration: PropTypes.number, // 切换动画持续的时间（单位是毫秒），默认为500
    scrollHeight: PropTypes.number, // 滚屏高度
    scrollStyle: ViewPropTypes.style,
    textStyle: Text.propTypes.style
  };

  static defaultProps = {
    animationEnable: true,
    scrollHeight: 32,
    delay: 500,
    duration: 500
  };

  constructor(props) {
    super(props);

    this.state = {
      translateValue: new Animated.ValueXY({x: 0, y: 0}),
      // 滚屏内容
      kb_content: [],
      // Animated.View 滚动到的 y轴坐标
      kb_tempValue: 0,
      // 最大偏移量
      kb_contentOffsetY: 0,
      enableAnimation: true,
    };

    this.state.translateValue.addListener(({x, y}) => {
      // Log('value',x,y)
    });
  }

  render() {
    return (
      <View style={[styles.kbContainer, {height: this.props.scrollHeight}]}>
        {this.state.kb_content.length !== 0 ?
            <Animated.View
              style={[
                {flexDirection: 'column'},
                {
                  transform: [
                    {translateY: this.state.translateValue.y}
                  ]
                }
              ]}>
              {this.state.kb_content.map(this._createKbItem.bind(this))}
            </Animated.View> : null
        }
      </View>
    )
  }

  _createKbItem(kbItem, index) {
    return (
      <View key={index} style={[{justifyContent: 'center', height: this.props.scrollHeight}, this.props.scrollStyle]}>
        <Text
          style={[styles.itemStyle, this.props.textStyle]}
          numberOfLines={1}
          ellipsizeMode={'tail'}>
            {kbItem.content}
          </Text>
      </View>
    )
  }

  componentDidMount() {
    this._computedData(this.props.data)
  }

  componentWillReceiveProps(nextProps) {
    this.setState({enableAnimation: !!nextProps.animationEnable}, () => this.startAnimation());
    this._computedData(nextProps.data)
  }

  _computedData(data) {
    let content = data || [];
    if (content.length !== 0) {
      let h = (content.length + 1) * this.props.scrollHeight;
      this.setState({
        kb_content: content.concat(content[0]),
        kb_contentOffsetY: h
      });

      // 开始动画
      this.startAnimation();
    }
  }

  startAnimation = () => {
    if (this.state.enableAnimation) {
      if (!this.animation) {
        this.animation = setTimeout(() => {
          this.animation = null;
          this._runAnimation();
        }, this.props.delay);
      }
    }
  };

  _runAnimation = () => {
    this.state.kb_tempValue -= this.props.scrollHeight;
    if (this.props.onChange) {
      let index = Math.abs(this.state.kb_tempValue) / (this.props.scrollHeight);
      this.props.onChange(index < this.state.kb_content.length - 1 ? index : 0);
    }
    Animated.sequence([

      // Animated.delay(this.state.delay),
      Animated.timing(
        this.state.translateValue,
        {
          isInteraction: false,
          toValue: {x: 0, y: this.state.kb_tempValue},
          duration: this.props.duration,
          easing: Easing.linear
        }
      ),
    ]).start(() => {
      // 无缝切换
      // Log('end')
      if (this.state.kb_tempValue - this.props.scrollHeight === -this.state.kb_contentOffsetY) {
        // 快速拉回到初始状态
        this.state.translateValue.setValue({x: 0, y: 0});
        this.state.kb_tempValue = 0;
      }
      this.startAnimation();
    })
  };

  componentWillUnmount() {
    this.animation && clearTimeout(this.animation);

    if (this.state.translateValue) {
      this.state.translateValue.removeAllListeners();
    }
  }

}

const styles = StyleSheet.create({
  kbContainer: {
    // 必须要有一个背景或者一个border，否则本身高度将不起作用
    backgroundColor: 'transparent',
    overflow: 'hidden'
  },
  itemStyle: {
    fontSize: 15,
    color: 'black',
  }
});