import React, { useState, useEffect } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Ionicons from 'react-native-vector-icons/Ionicons';
import { StyleSheet } from 'react-native';
import ProductListScreen from './screens/ProductListScreen';
import LoginScreen from './screens/LoginScreen';
import Dashboard from './screens/Dashboard';
import SettingsScreen from "./screens/SettingsScreen";
import SplashScreen from './screens/SplashScreen';
import OrderFormScreen from './screens/OrderFormScreen';
import BasketPage from './screens/BasketPage';
import OrdersPage from './screens/MyOrders';
import DeliveryPage from './screens/LivreurScreen';
import UserComments from './screens/MesComments';
import Mydeliveries from "./screens/Mydeliveries";
import SignUp from "./screens/SignUp";
import SignUpp from "./screens/SignUpDeliveryman";

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
            name="My basket"
            component={BasketPage}
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="cart-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
        <Tab.Screen
            name="My Orders"
            component={OrdersPage}
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="clipboard-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
        <Tab.Screen
            name="My Comments"
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
    </Tab.Navigator>
);

// Tab Navigator for deliveryman
const TabNavigatorDeliveryMan = () => (
    <Tab.Navigator
        screenOptions={{
            tabBarActiveTintColor: '#4CAF50',
            tabBarInactiveTintColor: 'gray',
            tabBarStyle: { backgroundColor: '#fff' },
        }}
    >
        <Tab.Screen
            name="Orders"
            component={DeliveryPage}
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="clipboard-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
        <Tab.Screen
            name="My deliveries"
            component={Mydeliveries} // Or a specific screen for delivered orders
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="checkmark-done-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
        <Tab.Screen
            name="logout"
            component={LoginScreen}
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="logout-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
    </Tab.Navigator>
);

const App = () => {
    const [userType, setUserType] = useState(null); // State to store user type

    useEffect(() => {
        // Simulating a login or fetching user data (replace with actual logic)
        const user = { type: 'deliveryman' }; // Replace with actual logic to fetch the user type
        setUserType(user.type); // Set user type when fetched
    }, []);

    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="SplashScreen">
                <Stack.Screen
                    name="SplashScreen"
                    component={SplashScreen}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="Login"
                    component={LoginScreen}
                    options={{ headerShown: false }}
                />
                {/* If user type is 'client', show TabNavigator */}
                {userType === 'client' && (
                    <Stack.Screen
                        name="Home"
                        component={TabNavigator}  // Show TabNavigator for clients
                        options={{ headerShown: false }}
                    />
                )}
                {/* If user type is 'deliveryman', show TabNavigatorDeliveryMan */}
                {userType === 'livreur' && (
                    <Stack.Screen
                        name="Home"
                        component={TabNavigatorDeliveryMan}  // Show TabNavigator for deliveryman
                        options={{ headerShown: false }}
                    />
                )}
                {/* Admin or other user types, show respective screens */}
                {userType === 'admin' && (
                    <Stack.Screen
                        name="Dashboard"
                        component={Dashboard}
                        options={{ headerShown: false }}
                    />
                )}
                {/* Other screens for both users */}
                <Stack.Screen
                    name="commandes"
                    component={DeliveryPage}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="parametres"
                    component={SettingsScreen}
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
