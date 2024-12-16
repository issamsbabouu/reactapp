import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, ActivityIndicator } from 'react-native';
import axios from 'axios';

const Mydeliveries = () => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

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
            <Text style={styles.header}>Orders for Delivery</Text>
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
    header: {
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 20,
        textAlign: 'center',
        color: '#333',
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
    cardTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 10,
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

export default Mydeliveries;
