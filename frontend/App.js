import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import SettingsScreen from './screens/SettingsScreen';
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
import SignUpp from "./screens/SignUpDeliveryman"; // Ensure this is the correct import

const Stack = createStackNavigator();
const Tab = createBottomTabNavigator();

const TabNavigator = () => (
    <Tab.Navigator
        screenOptions={{
            tabBarActiveTintColor: '#4CAF50',
            tabBarInactiveTintColor: 'gray',
            tabBarStyle: { backgroundColor: '#fff' },
        }}
    >
        <Tab.Screen
            name="Accueil"
            component={ProductListScreen}
            options={{ headerShown: false }}
        />
        <Tab.Screen
            name="Produits"
            component={ProductListScreen}
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="list-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
        <Tab.Screen
            name="Mon compte"
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="person-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
        <Tab.Screen
            name="Mes commandes"
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="receipt-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
        <Tab.Screen
            name="Paramètres"
            component={SettingsScreen}
            options={{
                tabBarIcon: ({ color, size }) => <Ionicons name="settings-outline" size={size} color={color} />,
                headerShown: false,
            }}
        />
    </Tab.Navigator>
);

const App = () => {
    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="Login">
                <Stack.Screen
                    name="Login"
                    component={LoginScreen}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="Home"
                    component={ProductListScreen}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="gusers"
                    component={GestionUsersadmn}
                    options={{ headerShown: false }}
                />
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
                    options={{ headerShown: false }}  // Customize header if needed
                />
                <Stack.Screen
                    name="SignUpp"
                    component={SignUpp}
                    options={{ headerShown: false }}  // Customize header if needed
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
