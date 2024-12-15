import React, { useState } from 'react';
import { View, TextInput, Button, Text, StyleSheet, Image, TouchableOpacity } from 'react-native';
import axios from 'axios';
import { useNavigation } from '@react-navigation/native';

const OrderForm = () => {
    const [phone, setPhone] = useState('');
    const [deliveryAddress, setDeliveryAddress] = useState('');
    const [clientName, setClientName] = useState('');
    const [date,setDate ] = useState('');
    const [error, setError] = useState(null);
    const [orderSuccess, setOrderSuccess] = useState(false);

    const navigation = useNavigation(); // Hook for navigation

    // Validate form data
    const validateForm = () => {
        if (!clientName || !deliveryAddress || !livreurName || !quantity || productIds.length === 0) {
            setError('All fields are required');
            return false;
        }
        if (isNaN(quantity) || quantity <= 0) {
            setError('Quantity must be a positive number');
            return false;
        }
        setError(null);
        return true;
    };

    const handleSubmit = () => {
        if (!validateForm()) return;

        const orderData = {
            clientName,
            deliveryAddress,
            productIds,
            quantity: parseInt(quantity, 10),
            livreurName,
        };

        axios
            .post('http://localhost:8080/api/orders', orderData) // Make sure to update with your backend URL
            .then((response) => {
                setOrderSuccess(true);
                console.log('Order Created:', response.data);
            })
            .catch((error) => {
                setError('An error occurred while creating the order');
                console.error(error);
            });
    };

    const handleCancel = () => {
        navigation.goBack(); // Navigate back to the previous screen
    };

    return (
        <View style={styles.container}>
            <Image source={require('./LOGO.png')} style={styles.logo} />
            <Text style={styles.title}>Please fill informations of your delivery</Text>
            <Text>Your full Name</Text>
            <TextInput
                style={{ borderWidth: 1, marginBottom: 10, padding: 8 }}
                value={clientName}
                onChangeText={setClientName}
            />

            <Text>Your delivery address</Text>
            <TextInput
                style={{ borderWidth: 1, marginBottom: 10, padding: 8 }}
                value={deliveryAddress}
                onChangeText={setDeliveryAddress}
            />

            <Text>Your delivery date</Text>
            <TextInput
                style={{ borderWidth: 1, marginBottom: 10, padding: 8 }}
                value={date}
                onChangeText={setDate}
            />

            <Text>GSM</Text>
            <TextInput
                style={{ borderWidth: 1, marginBottom: 10, padding: 8 }}
                value={phone}
                onChangeText={setPhone}
            />
            <TouchableOpacity style={styles.submitbutton} onPress={handleSubmit}>
                <Text style={styles.cancelButtonText}>Submit</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.cancelButton} onPress={handleCancel}>
                <Text style={styles.cancelButtonText}>Cancel</Text>
            </TouchableOpacity>

            {error && <Text style={{ color: 'red', marginTop: 10 }}>{error}</Text>}
            {orderSuccess && <Text style={{ color: 'green', marginTop: 10 }}>Order Created Successfully!</Text>}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        marginTop: 60,
        padding: 50,
        flex: 1,
    },
    title: {
        fontSize: 20,
        alignSelf: 'center',
    },
    logo: {
        width: 150,
        height: 150,
        marginBottom: 20,
        alignSelf: 'center',
    },
    cancelButton: {
        marginTop: 20,
        padding: 10,
        backgroundColor: '#FF5555',
        borderRadius: 5,
        alignItems: 'center',
    },
    submitbutton: {
        marginTop: 20,
        padding: 10,
        backgroundColor: 'green',
        borderRadius: 5,
        alignItems: 'center',
    },
    cancelButtonText: {
        color: 'white',
        fontWeight: 'bold',
    },
});

export default OrderForm;
