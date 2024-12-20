import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, ActivityIndicator, Alert, Image } from 'react-native';
import axios from 'axios';
import Ionicons from "react-native-vector-icons/Ionicons";
import { useNavigation } from '@react-navigation/native';  // Import useNavigation hook

const DeliveryPage = ({ deliverymanId }) => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [isMenuVisible, setMenuVisible] = useState(false);
    const navigation = useNavigation();  // Initialize navigation hook

    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = async () => {
        try {
            setLoading(true);
            const response = await axios.get('http://localhost:8080/api/orders/commandes');
            setOrders(response.data);
        } catch (error) {
            Alert.alert('Error', 'Failed to fetch orders');
        } finally {
            setLoading(false);
        }
    };

    const handleTakeDelivery = async (orderId) => {
        try {
            const response = await axios.post(
                'http://127.0.0.1:8080/api/orders/assign-deliveryman',
                null,
                {
                    params: { orderId },
                    withCredentials: true, // Ensures cookies (including the session ID) are sent with the request
                }
            );

            alert(response.data.message); // Show success message
            fetchOrders(); // Refresh the order list after taking delivery
        } catch (error) {
            console.error("Error assigning delivery:", error);
            alert("Failed to take delivery. Please try again.");
        }
    };

    const toggleMenu = () => {
        setMenuVisible(prevState => !prevState);  // Toggle the menu visibility
    };

    // Function to handle refresh
    const handleRefresh = () => {
        fetchOrders();  // Refresh the order list
    };

    // Filter out 'TAKEN' orders before rendering
    const availableOrders = orders.filter(item => item.status !== 'TAKEN');

    return (
        <View style={styles.container}>
            <TouchableOpacity onPress={toggleMenu} style={styles.menuButton}>
                <Ionicons name="menu" size={30} color="black" />
            </TouchableOpacity>
            {isMenuVisible && (
                <View style={styles.menu}>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('deliveries')}>
                        <Text style={styles.menuText}>My deliveries</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Login')}>
                        <Text style={styles.menuText}>Logout</Text>
                    </TouchableOpacity>
                </View>
            )}
            <Text style={styles.header}>Available Orders</Text>
            {loading ? (
                <ActivityIndicator size="large" color="#4CAF50" />
            ) : (
                <>
                    {/* Refresh Button */}
                    <TouchableOpacity style={styles.refreshButton} onPress={handleRefresh}>
                        <Text style={styles.refreshButtonText}>Refresh</Text>
                    </TouchableOpacity>
                    <FlatList
                        data={availableOrders}
                        keyExtractor={(item) => item.id.toString()}
                        renderItem={({ item }) => (
                            <View
                                style={[
                                    styles.card,
                                    item.status === 'TAKEN' && styles.takenCard  // Conditionally apply green background if status is 'TAKEN'
                                ]}
                            >
                                {item.product ? (
                                    <>
                                        <Text style={[
                                            styles.productText,
                                            item.status === 'TAKEN' && styles.whiteText  // Make text white if the status is 'TAKEN'
                                        ]}>
                                            Product: {item.product.label}
                                        </Text>
                                        <Text style={[
                                            styles.productText,
                                            item.status === 'TAKEN' && styles.whiteText  // Make text white if the status is 'TAKEN'
                                        ]}>
                                            Quantity: {item.quantity}
                                        </Text>
                                    </>
                                ) : (
                                    <Text style={[
                                        styles.productText,
                                        item.status === 'TAKEN' && styles.whiteText  // Make text white if the status is 'TAKEN'
                                    ]}>
                                        No product data
                                    </Text>
                                )}

                                {/* Display the image only when the status is "TAKEN" */}
                                {item.status === 'TAKEN' && (
                                    <Image source={{ uri: 'http://localhost:8080/taken_order_image.jpg' }} style={styles.imaget} />
                                )}

                                {/* Conditionally render the "Take Delivery" button */}
                                {item.status !== 'TAKEN' && (
                                    <TouchableOpacity
                                        style={styles.button}
                                        onPress={() => handleTakeDelivery(item.id)}
                                    >
                                        <Text style={styles.buttonText}>Take Delivery</Text>
                                    </TouchableOpacity>
                                )}
                            </View>
                        )}
                    />
                </>
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
        marginTop:40,
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 20,
    },
    card: {
        backgroundColor: '#fff',  // Default background color
        padding: 15,
        marginBottom: 15,
        borderRadius: 10,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 5,
        elevation: 3,
    },
    takenCard: {
        backgroundColor: '#4CAF50',  // Green background for 'TAKEN' status
    },
    button: {
        backgroundColor: '#4CAF50',
        paddingVertical: 10,
        borderRadius: 5,
        marginTop: 10,
        alignItems: 'center',
    },
    buttonText: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold',
    },
    image: {
        marginTop: 10,
        width: '100%',
        height: 200,
        borderRadius: 10,
        resizeMode: 'cover',
    },
    imaget: {
        marginTop: 10,
        width: '100%',
        height: 70,
        borderRadius: 10,
        resizeMode: 'cover',
    },
    menuButton: {
        marginTop:20,
        padding: 10,
    },
    menu: {
        position: 'absolute',
        top: 50,
        left: 0,
        backgroundColor: 'white',
        borderWidth: 1,
        borderColor: '#ddd',
        width: 200,
        padding: 10,
    },
    menuItem: {
        padding: 10,
    },
    menuText: {
        fontSize: 16,
    },
    productText: {
        fontSize: 16,
        marginBottom: 5,
    },
    whiteText: {
        color: '#fff',  // White text for 'TAKEN' status
    },
    refreshButton: {
        backgroundColor: '#4CAF50',
        paddingVertical: 10,
        borderRadius: 5,
        marginBottom: 10,
        alignItems: 'center',
    },
    refreshButtonText: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold',
    },
});

export default DeliveryPage;
