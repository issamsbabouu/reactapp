import React, { useState, useEffect } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import ProductListScreen from './screens/ProductListScreen';
import LoginScreen from './screens/LoginScreen';
import GestionUsersadmn from './screens/GestionUsersadmn';
import Gestionprods from './screens/Gestionprods';
import GestionCateg from './screens/GestionCateg';
import Ionicons from 'react-native-vector-icons/Ionicons';
import { StyleSheet } from 'react-native';
import Dashboard from './screens/Dashboard';
import GestionCommentaires from './screens/GestionCommentaire';
import SignUp from "./screens/SignUp";
import SignUpp from "./screens/SignUpDeliveryman";
import UserComments from "./screens/MesComments"; // Ensure this is the correct import
import MyOrders from "./screens/MyOrders";
import SettingsScreen from "./screens/SettingsScreen";
import SplashScreen from "./screens/SplashScreen";
import OrderFormScreen from "./screens/OrderFormScreen";

const Stack = createStackNavigator();
const Tab = createBottomTabNavigator();

// Tab Navigator for clients
const TabNavigator = () => (
    <Tab.Navigator
        screenOptions={{
            tabBarActiveTintColor: '#4CAF50',
            tabBarInactiveTintColor: 'gray',
            tabBarStyle: { backgroundColor: '#fff' },
        }}
    >
        <Tab.Screen
            name="Products"
            component={ProductListScreen}
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="home-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
        <Tab.Screen
            name="My Orders"
            component={MyOrders}
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="cart-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
        <Tab.Screen
            name="Mes Commentaires"
            component={UserComments}
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="chatbubble-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
        <Tab.Screen
            name="Settings"
            component={SettingsScreen}
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="settings-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
        <Tab.Screen
            name="Logout"
            component={LoginScreen}
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="log-out-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
    </Tab.Navigator>
);

const App = () => {
    const [userType, setUserType] = useState(null); // State to store user type

    useEffect(() => {
        // Fetch user data (you could get this from a login or authentication API)
        const user = { type: 'client' }; // This is just an example. Replace with actual logic
        setUserType(user.type); // Set user type when fetched
    }, []);

    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="SplashScreen">
                <Stack.Screen name="SplashScreen" component={SplashScreen}
                              options={{ headerShown: false }}/>
                <Stack.Screen
                    name="Login"
                    component={LoginScreen}
                    options={{ headerShown: false }}
                />
                {/* Only show TabNavigator if user is a client */}
                {userType === 'client' && (
                    <Stack.Screen
                        name="Home"
                        component={ProductListScreen}
                        options={{ headerShown: false }}
                    />

                )}
                <Stack.Screen
                    name="gusers"
                    component={GestionUsersadmn}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="mescommentaires"
                    component={UserComments}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="parametres"
                    component={SettingsScreen}
                    options={{ headerShown: false }}
                />
                <Stack.Screen name="OrderForm" component={OrderFormScreen} options={{ headerShown: false }} />
                <Stack.Screen
                    name="Gestionprods"
                    component={Gestionprods}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="Gestioncateg"
                    component={GestionCateg}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="dashboard"
                    component={Dashboard}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="gestioncomment"
                    component={GestionCommentaires}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="SignUp"
                    component={SignUp}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="SignUpp"
                    component={SignUpp}
                    options={{ headerShown: false }}
                />
            </Stack.Navigator>
        </NavigationContainer>
    );
};

const styles = StyleSheet.create({
    screenContainer: {
        flex: 1,
        paddingTop: 20,
    },
    header: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingTop: 40,
        padding: 16,
        backgroundColor: 'white',
        height: 90,
        elevation: 5,
    },
    headerButton: {
        padding: 10,
    },
    headerTitle: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#4CAF50',
    },
});

export default App;
