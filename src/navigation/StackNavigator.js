import {Tabs} from "./TabNavigator";
import RootScreen from "../screen/RootScreen";
import HomeScreen from "../screen/HomeScreen";
import OneScreen from "../screen/OneScreen";
import LoginScreen from "../screen/LoginScreen";

/*需要注册的页面*/
export default {
  Root: {screen: RootScreen},
  MainTab: {screen: Tabs},
  Home: {screen: HomeScreen},
  One: {screen: OneScreen},
  Login: {screen: LoginScreen},
};