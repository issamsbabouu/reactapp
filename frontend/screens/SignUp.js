import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from 'react-native';
import axios from 'axios';
import { useNavigation } from '@react-navigation/native'; // To handle navigation back

const SignUp = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [nom, setNom] = useState('');
    const [phone, setPhone] = useState(''); // State for phone number

    const navigation = useNavigation(); // For navigation

    const handleSignUp = async () => {
        const signUpData = {
            username,
            email,
            password,
            nom,
            phone // Include phone in the sign-up data
        };

        try {
            const response = await axios.post('http://localhost:8080/apii/signuping', signUpData);
            Alert.alert('Success', 'You have signed up successfully!');
            // Optionally navigate to another screen after successful sign-up
        } catch (error) {
            console.error(error);
            Alert.alert('Error', 'Sign-up failed. Please try again.');
        }
    };

    const resetFields = () => {
        setUsername('');
        setEmail('');
        setPassword('');
        setNom('');
        setPhone(''); // Reset phone field
    };

    return (
        <View style={styles.container}>
            <Text style={styles.header}>Sign Up</Text>

            <TextInput
                style={styles.input}
                placeholder="Username"
                value={username}
                onChangeText={setUsername}
                placeholderTextColor="black"
            />
            <TextInput
                style={styles.input}
                placeholder="Email"
                value={email}
                onChangeText={setEmail}
                placeholderTextColor="black"
            />
            <TextInput
                style={styles.input}
                placeholder="Password"
                secureTextEntry
                value={password}
                onChangeText={setPassword}
                placeholderTextColor="black"
            />
            <TextInput
                style={styles.input}
                placeholder="Nom"
                value={nom}
                onChangeText={setNom}
                placeholderTextColor="black"
            />
            <TextInput
                style={styles.input}
                placeholder="Phone"
                value={phone}
                onChangeText={setPhone}
                placeholderTextColor="black"
                keyboardType="phone-pad"
            />

            <TouchableOpacity style={styles.button} onPress={handleSignUp}>
                <Text style={styles.buttonText}>Sign Up</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.button} onPress={resetFields}>
                <Text style={styles.buttonText}>Reset Fields</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.backButton} onPress={() => navigation.goBack()}>
                <Text style={styles.buttonText}>Go Back</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 20,
        backgroundColor: '#f5f5f5',
    },
    header: {
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 20,
        textAlign: 'center',
        color: 'black',
    },
    input: {
        height: 40,
        borderColor: '#ccc',
        borderWidth: 1,
        borderRadius: 8,
        marginBottom: 15,
        paddingLeft: 10,
        color: 'black',
    },
    button: {
        backgroundColor: '#4CAF50',
        padding: 10,
        borderRadius: 5,
        marginBottom: 15,
        alignItems: 'center',
    },
    backButton: {
        backgroundColor: '#FF6347',
        padding: 10,
        borderRadius: 5,
        marginBottom: 15,
        alignItems: 'center',
    },
    buttonText: {
        color: '#fff',
        fontSize: 16,
    },
});

export default SignUp;
