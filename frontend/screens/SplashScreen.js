// SplashScreen.js
import React, { useEffect } from 'react';
import { View, Text, StyleSheet, Animated, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native';

const SplashScreen = () => {
    const navigation = useNavigation();
    const progress = new Animated.Value(0);

    useEffect(() => {
        // Animate the loading bar over 3 seconds
        Animated.timing(progress, {
            toValue: 1,
            duration: 3000,
            useNativeDriver: false,
        }).start();
        setTimeout(() => {
            navigation.replace('Login'); // Assuming 'Login' is the name of your login screen in the navigator
        }, 4000);
    }, [navigation]);

    return (
        <View style={styles.container}>
            <Image source={require('./LOGO.png')} style={styles.logo} />
            <Text style={styles.title}>Loading . . .</Text>
            <Animated.View style={[styles.loadingBar, { width: progress.interpolate({ inputRange: [0, 1], outputRange: ['0%', '100%'] }) }]} />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#fff',
    },
    logo: {
        width: 150,
        height: 150,
        marginBottom: 20,
    },
    loadingBar: {
        height: 5,
        backgroundColor: '#007BFF',
        marginTop: 20,
    },
});

export default SplashScreen;
