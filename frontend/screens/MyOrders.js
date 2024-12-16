import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, StyleSheet, Image, ActivityIndicator } from 'react-native';
import axios from 'axios';

const OrdersPage = () => {
    const [orders, setOrders] = useState([]); // Liste des commandes
    const [loading, setLoading] = useState(true); // Indicateur de chargement
    const [error, setError] = useState(null); // Gestion des erreurs

    // Charger les commandes au montage du composant
    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = async () => {
        try {
            setLoading(true); // Start loading
            setError(null); // Reset any previous errors

            const response = await axios.get('http://127.0.0.1:8080/api/orders/user', {
                withCredentials: true, // Include session cookies
            });

            console.log('Orders fetched:', response.data); // Log the raw response data

            if (response.data && Array.isArray(response.data)) {
                setOrders(response.data); // Update the orders state
                console.log('Order data:', response.data); // Inspect the orders data
            } else {
                console.log('No orders found');
                setOrders([]); // Reset the orders if none are found
            }
        } catch (error) {
            console.error('Error fetching orders:', error.response ? error.response.data : error);
            setError('Failed to fetch orders. Please try again later.');
        } finally {
            setLoading(false); // End loading
        }
    };
    const renderOrder = ({ item }) => (
        <View style={styles.orderCard}>
            {/* If you have an image, replace it here */}
            <View style={styles.placeholderImage} />
            <View style={styles.orderInfo}>
                <Text style={styles.productName}>{item.productName || 'Unnamed Product'}</Text>
                <Text style={styles.productPrice}>Price: Not Available</Text>
                <Text style={styles.productQuantity}>Quantity: {item.quantity}</Text>
            </View>
        </View>
    );

    return (
        <View style={styles.container}>
            <Text style={styles.header}>My Orders</Text>
            {loading ? (
                <ActivityIndicator size="large" color="#4CAF50" style={styles.loader} />
            ) : error ? (
                <Text style={styles.errorText}>{error}</Text>
            ) : orders.length === 0 ? (
                <Text style={styles.noOrdersText}>No orders found.</Text>
            ) : (
                <FlatList
                    data={orders}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={renderOrder}
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
        marginTop: 50,
    },
    header: {
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 20,
        textAlign: 'center',
    },
    loader: {
        marginTop: 50,
    },
    errorText: {
        color: 'red',
        fontSize: 16,
        textAlign: 'center',
        marginTop: 20,
    },
    noOrdersText: {
        fontSize: 16,
        textAlign: 'center',
        color: '#888',
        marginTop: 20,
    },
    orderCard: {
        backgroundColor: '#fff',
        padding: 15,
        marginBottom: 15,
        borderRadius: 10,
        flexDirection: 'row',
        alignItems: 'center',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 5,
        elevation: 3,
    },
    productImage: {
        width: 80,
        height: 80,
        borderRadius: 8,
    },
    orderInfo: {
        flex: 1,
        marginLeft: 10,
    },
    productName: {
        fontSize: 16,
        fontWeight: 'bold',
    },
    productPrice: {
        fontSize: 14,
        color: '#888',
    },
    productQuantity: {
        fontSize: 14,
        marginVertical: 5,
    },
});

export default OrdersPage;
