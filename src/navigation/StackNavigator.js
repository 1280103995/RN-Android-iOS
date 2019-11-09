import {Tabs} from "./TabNavigator";
import RootScreen from "../screen/RootScreen";
import TestOneScreen from "../screen/TestOneScreen";
import LoginScreen from "../screen/LoginScreen";
import TestTwoScreen from '../screen/TestTwoScreen';
import TestThreeScreen from '../screen/TestThreeScreen';

/*需要注册的页面*/
export default {
  Root: {screen: RootScreen},
  MainTab: {screen: Tabs},
  TestOne: {screen: TestOneScreen},
  TestTwo: {screen: TestTwoScreen},
  TestThree: {screen: TestThreeScreen},
  Login: {screen: LoginScreen},
};
