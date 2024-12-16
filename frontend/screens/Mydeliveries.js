import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, StyleSheet, ActivityIndicator, Alert } from 'react-native';
import axios from 'axios';

const Mydeliveries = () => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Fetch orders for the connected deliveryman
    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = async () => {
        try {
            setLoading(true);
            setError(null);
            const response = await axios.get('http://127.0.0.1:8080/api/orders/livreur', {
                withCredentials: true, // Ensure session cookies are sent
            });
            console.log(response.data);  // Log API response to inspect the data structure
            setOrders(response.data);
        } catch (error) {
            setError('Failed to fetch orders');
        } finally {
            setLoading(false);
        }
    };

    return (
        <View style={styles.container}>
            <Text style={styles.header}>Your Deliveries</Text>
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
                            {/* Product Info */}
                            <Text style={styles.productText}>Product: {item.productLabel || 'No product label available'}</Text>
                            <Text style={styles.productText}>Quantity: {item.quantity || 'Unknown quantity'}</Text>

                            {/* Client Info */}
                            <Text style={styles.clientText}>Client Name: {item.clientName || 'Unknown Client'}</Text>
                            <Text style={styles.clientText}>Client Phone: {item.clientPhone || 'Unknown Phone'}</Text>

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
        marginTop: 50,
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
    productText: {
        fontSize: 16,
        color: '#555',
        marginBottom: 5,
    },
    clientText: {
        fontSize: 16,
        color: '#333',
        marginBottom: 5,
    },
    errorText: {
        color: 'red',
        textAlign: 'center',
        marginTop: 20,
    },
});

export default Mydeliveries;
