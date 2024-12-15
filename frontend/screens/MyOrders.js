import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList, ActivityIndicator, TouchableOpacity } from 'react-native';
import axios from 'axios';

const MyOrdersScreen = () => {
    const [orders, setOrders] = useState([]); // Orders list
    const [loading, setLoading] = useState(true); // Loading indicator

    // Fetch orders data from the API
    useEffect(() => {
        axios.get('http://localhost:8080/api/orders')
            .then(response => {
                console.log("Orders fetched:", response.data);  // Debugging
                setOrders(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching orders:', error);
                setLoading(false);
            });
    }, []);

    // If data is still loading, show a loading indicator
    if (loading) {
        return (
            <View style={styles.loadingContainer}>
                <ActivityIndicator size="large" color="#3498db" />
                <Text style={styles.loadingText}>Chargement des commandes...</Text>
            </View>
        );
    }

    return (
        <View style={styles.container}>
            {/* Orders list */}
            <FlatList
                data={orders}
                keyExtractor={(item) => item.id.toString()}
                renderItem={({ item }) => (
                    <View style={styles.orderCard}>
                        <Text style={styles.orderTitle}>Commande #{item.id}</Text>
                        <Text style={styles.orderStatus}>Statut: {item.status}</Text>
                        <Text style={styles.orderTotal}>Total: {item.totalAmount} MAD</Text>
                        <TouchableOpacity
                            style={styles.viewDetailsButton}
                            onPress={() => alert(`Viewing details for order #${item.id}`)}  // You can add navigation here
                        >
                            <Text style={styles.viewDetailsText}>Voir les détails</Text>
                        </TouchableOpacity>
                    </View>
                )}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 16,
        backgroundColor: '#ecf0f1',
    },
    loadingContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    loadingText: {
        fontSize: 18,
        color: '#3498db',
        marginTop: 10,
    },
    orderCard: {
        backgroundColor: '#fff',
        borderRadius: 12,
        padding: 16,
        marginBottom: 16,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 4 },
        shadowOpacity: 0.1,
        shadowRadius: 8,
    },
    orderTitle: {
        fontSize: 18,
        fontWeight: '600',
        color: '#34495e',
    },
    orderStatus: {
        fontSize: 14,
        color: '#7f8c8d',
        marginTop: 8,
    },
    orderTotal: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#3498db',
        marginTop: 8,
    },
    viewDetailsButton: {
        marginTop: 10,
        backgroundColor: '#3498db',
        paddingVertical: 8,
        paddingHorizontal: 15,
        borderRadius: 25,
    },
    viewDetailsText: {
        color: '#fff',
        fontWeight: 'bold',
    },
});

export default MyOrdersScreen;
