import React, { useState, useEffect } from 'react';
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
import SignUpp from "./screens/SignUpDeliveryman";
import CommentsPage from "./screens/MesComments";
import DeliveryPage from "./screens/LivreurScreen";
import Mydeliveries from "./screens/Mydeliveries"; // Ensure this is the correct import

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
    const [userType, setUserType] = useState(null); // State to store user type

    useEffect(() => {
        // Simulating a login or fetching user data (replace with actual logic)
        const user = { type: 'deliveryman' }; // Replace with actual logic to fetch the user type
        setUserType(user.type); // Set user type when fetched
    }, []);

    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="Login">
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
                        name="Orders"
                        component={TabNavigatorDeliveryMan}
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
                    name="mescommentaires"
                    component={CommentsPage}
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
                <Stack.Screen
                    name="Orders"
                    component={DeliveryPage}
                    options={{ headerShown: false }}
                />
                <Stack.Screen
                    name="deliveries"
                    component={Mydeliveries}
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
