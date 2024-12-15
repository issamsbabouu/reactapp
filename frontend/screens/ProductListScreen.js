import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, TextInput, ActivityIndicator, FlatList, TouchableOpacity, Image, Modal, Button } from 'react-native';
import axios from 'axios';

const ProductListScreen = ({ navigation }) => {
    const [products, setProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);
    const [searchQuery, setSearchQuery] = useState('');
    const [loading, setLoading] = useState(true);
    const [basket, setBasket] = useState([]);
    const [deliveryAddress, setDeliveryAddress] = useState('');
    const [quantity, setQuantity] = useState(1);
    const [isFormVisible, setIsFormVisible] = useState(false);
    const [isBasketVisible, setIsBasketVisible] = useState(false);

    const handleAddToBasket = (product) => {
        const existingProductIndex = basket.findIndex(item => item.id === product.id);
        if (existingProductIndex >= 0) {
            const updatedBasket = [...basket];
            updatedBasket[existingProductIndex].quantity += quantity;
            setBasket(updatedBasket);
        } else {
            setBasket([...basket, { ...product, quantity }]);
        }
    };

    useEffect(() => {
        axios.get('http://localhost:8080/api/products')
            .then(response => {
                setProducts(response.data);
                setFilteredProducts(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching products', error);
                setLoading(false);
            });
    }, []);

    const handleSearchChange = (query) => {
        setSearchQuery(query);
        filterProducts(query);
    };

    const filterProducts = (query) => {
        let filtered = products;
        if (query) filtered = filtered.filter(product => product.label.toLowerCase().includes(query.toLowerCase()));
        setFilteredProducts(filtered);
    };

    const handleToggleBasket = () => {
        setIsBasketVisible(!isBasketVisible);
    };

    const handleRemoveFromBasket = (productId) => {
        setBasket(basket.filter(item => item.id !== productId));
    };

    const handleSubmitOrder = () => {
        const orderData = {
            productIds: basket.map(item => item.id),
            deliveryAddress,
            quantity,
            totalAmount: basket.reduce((sum, item) => sum + item.price * item.quantity, 0)
        };

        axios.post('http://localhost:8080/api/commands', orderData)
            .then(response => {
                console.log('Order placed:', response.data);
                setBasket([]);
                setIsFormVisible(false);
            })
            .catch(error => {
                console.error('Error placing order:', error);
            });
    };

    if (loading) {
        return (
            <View style={styles.loadingContainer}>
                <ActivityIndicator size="large" color="#3498db" />
                <Text style={styles.loadingText}>Loading products...</Text>
            </View>
        );
    }

    return (
        <View style={styles.container}>
            <TextInput
                style={styles.searchBar}
                placeholder="Search for products"
                value={searchQuery}
                onChangeText={handleSearchChange}
            />

            {/* Basket Button */}
            <TouchableOpacity style={styles.basketButton} onPress={handleToggleBasket}>
                <Text style={styles.basketText}>Basket ({basket.length} items)</Text>
            </TouchableOpacity>

            <FlatList
                data={filteredProducts}
                keyExtractor={(item) => item.id.toString()}
                numColumns={2}
                renderItem={({ item }) => (
                    <View style={styles.productCard}>
                        <Image source={{ uri: item.photo }} style={styles.productImage} />
                        <Text style={styles.productName}>{item.label}</Text>
                        <Text style={styles.productPrice}>Price: {item.price} MAD</Text>
                        <TouchableOpacity
                            style={styles.addToBasketButton}
                            onPress={() => handleAddToBasket(item)}
                        >
                            <Text style={styles.addToBasketText}>Add to basket</Text>
                        </TouchableOpacity>
                    </View>
                )}
            />

            {/* Basket Modal */}
            {isBasketVisible && (
                <Modal visible={isBasketVisible} animationType="slide" transparent={true} onRequestClose={handleToggleBasket}>
                    <View style={styles.modalOverlay}>
                        <View style={styles.basketModalContainer}>
                            <Text style={styles.modalTitle}>Your Basket</Text>
                            <FlatList
                                data={basket}
                                keyExtractor={(item) => item.id.toString()}
                                renderItem={({ item }) => (
                                    <View style={styles.basketItem}>
                                        <Text>{item.label} - {item.quantity} x {item.price} MAD</Text>
                                        <TouchableOpacity onPress={() => handleRemoveFromBasket(item.id)}>
                                            <Text style={styles.removeText}>Remove</Text>
                                        </TouchableOpacity>
                                    </View>
                                )}
                            />
                            <Button
                                title="Proceed to Order"
                                onPress={() => {
                                    handleToggleBasket();
                                    navigation.navigate('OrderForm', { basket }); // Pass basket data to the OrderForm screen
                                }}
                            />
                            <Button title="Close" onPress={handleToggleBasket} />
                        </View>
                    </View>
                </Modal>
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        marginTop: 80, flex: 1, padding: 10
    },
    searchBar: { height: 40, borderColor: 'gray', borderWidth: 1, marginBottom: 10, paddingLeft: 8 },
    loadingContainer: { flex: 1, justifyContent: 'center', alignItems: 'center' },
    loadingText: { marginTop: 10 },
    productCard: { width: '48%', marginBottom: 20, padding: 10, backgroundColor: '#f9f9f9', borderRadius: 10 },
    productImage: { width: '100%', height: 150, borderRadius: 10 },
    productName: { fontSize: 16, fontWeight: 'bold', marginVertical: 5 },
    productPrice: { fontSize: 14, marginBottom: 10 },
    addToBasketButton: { backgroundColor: '#3498db', padding: 10, borderRadius: 5 },
    addToBasketText: { color: '#fff', textAlign: 'center' },
    modalOverlay: { flex: 1, justifyContent: 'center', alignItems: 'center', backgroundColor: 'rgba(0,0,0,0.5)' },
    basketModalContainer: {
        backgroundColor: '#fff',
        padding: 20,
        borderRadius: 10,
        width: '80%',
        maxHeight: '70%'
    },
    modalTitle: { fontSize: 18, fontWeight: 'bold', marginBottom: 15 },
    basketItem: { marginBottom: 10 },
    removeText: { color: 'red', textDecorationLine: 'underline' },
    basketButton: { backgroundColor: '#3498db', padding: 10, marginBottom: 20, borderRadius: 5 },
    basketText: { color: '#fff', textAlign: 'center' }
});

export default ProductListScreen;
