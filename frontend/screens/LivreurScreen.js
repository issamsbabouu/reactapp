import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, ActivityIndicator } from 'react-native';
import axios from 'axios';
import Ionicons from 'react-native-vector-icons/Ionicons';

const DeliveryPage = ({ navigation }) => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isMenuVisible, setMenuVisible] = useState(false);  // State for menu visibility

    // Fetch orders from the backend
    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = async () => {
        try {
            setLoading(true);
            setError(null);
            const response = await axios.get('http://localhost:8080/api/orders/commandes');
            setOrders(response.data);
        } catch (error) {
            setError('Failed to fetch orders');
        } finally {
            setLoading(false);
        }
    };

    const handleTakeDelivery = (orderId) => {
        // Handle delivery action here
        alert(`Order ${orderId} is being delivered`);
    };

    return (
        <View style={styles.container}>
            {/* Menu Button */}
            <TouchableOpacity onPress={() => setMenuVisible(!isMenuVisible)} style={styles.menuButton}>
                <Ionicons name="menu" size={30} color="black" />
            </TouchableOpacity>

            {/* Menu */}
            {isMenuVisible && (
                <View style={styles.menu}>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('My deliveries')}>
                        <Text style={styles.menuText}>My deliveries</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Login')}>
                        <Text style={styles.menuText}>Log out</Text>
                    </TouchableOpacity>
                </View>
            )}

            <Text style={styles.header}>Orders to Deliver</Text>
            {loading ? (
                <ActivityIndicator size="large" color="#4CAF50" />
            ) : error ? (
                <Text style={styles.errorText}>{error}</Text>
            ) : (
                <FlatList
                    data={orders}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={({ item }) => (
                        <View style={styles.card}>
                            {item.product ? (
                                <>
                                    <Text style={styles.productText}>Product: {item.product.label}</Text>
                                    <Text style={styles.productText}>Quantity: {item.quantity}</Text>
                                </>
                            ) : (
                                <Text>No product data</Text>
                            )}
                            <TouchableOpacity
                                style={styles.button}
                                onPress={() => handleTakeDelivery(item.id)}
                            >
                                <Text style={styles.buttonText}>Take Delivery</Text>
                            </TouchableOpacity>
                        </View>
                    )}
                />
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: '#f9f9f9',
    },
    menuButton: {
        position: 'absolute',
        top: 20,
        left: 20,
        zIndex: 10,
        marginTop:20,
        padding: 10,
    },
    menu: {
        position: 'absolute',
        top: 60,
        left: 20,
        backgroundColor: '#fff',
        padding: 10,
        borderRadius: 8,
        shadowColor: '#000',
        shadowOpacity: 0.2,
        shadowRadius: 5,
        elevation: 3,
        minWidth: 200,
        zIndex: 1000,
    },
    menuItem: {
        paddingVertical: 15,
        paddingHorizontal: 20,
        borderBottomWidth: 1,
        borderColor: '#ddd',
    },
    menuText: {
        fontSize: 16,
        color: '#333',
    },
    header: {
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 20,
        textAlign: 'center',
        color: '#333',
        marginTop:60,
    },
    card: {
        backgroundColor: '#fff',
        padding: 15,
        marginBottom: 15,
        borderRadius: 10,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 5,
        elevation: 3,
    },
    productText: {
        fontSize: 16,
        color: '#555',
        marginBottom: 5,
    },
    button: {
        backgroundColor: '#4CAF50',
        paddingVertical: 10,
        borderRadius: 5,
        marginTop: 15,
        alignItems: 'center',
    },
    buttonText: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold',
    },
    errorText: {
        color: 'red',
        textAlign: 'center',
        marginTop: 20,
    },
});

export default DeliveryPage;
