import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, TextInput, ActivityIndicator, FlatList, TouchableOpacity, Image, Modal, Button } from 'react-native';
import axios from 'axios';

const ProductListScreen = ({ navigation }) => {
    const [products, setProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);
    const [searchQuery, setSearchQuery] = useState('');
    const [loading, setLoading] = useState(true);
    const [basket, setBasket] = useState([]);
    const [isBasketVisible, setIsBasketVisible] = useState(false);
    const [authToken, setAuthToken] = useState(''); // Assuming you store the auth token after login

    // Fetch the products from the backend
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
    const handleAddToBasket = async (product, quantity) => {
        if (!product) {
            console.error('No product selected');
            return;
        }

        try {
            // Ensure you're passing the correct parameters in the request body
            const requestBody = {
                productId: product.id,  // Use the product id from the selected product
                quantity: quantity      // Quantity passed as argument or default
            };

            console.log("Request Body:", requestBody);  // Log the request body to check

            const response = await axios.post('http://127.0.0.1:8080/api/panier/add', requestBody, {
                headers: {
                    'Content-Type': 'application/json',  // Ensure the content type is set to JSON
                },
                withCredentials: true  // Include session cookies with the request
            });

            console.log('Product added to basket:', response.data);
        } catch (error) {
            console.error('Error adding to basket:', error.response ? error.response.data : error.message);
        }
    };


    const handleToggleBasket = () => {
        setIsBasketVisible(!isBasketVisible);
    };
    const fetchPanier = async () => {
        try {
            const response = await axios.get('http://127.0.0.1:8080/api/panier/user', {
                headers: {
                    Authorization: `Bearer ${authToken}`,
                },
            });
            setBasket(response.data.commandes); // Assuming panier contains commandes list
        } catch (error) {
            console.error('Error fetching panier:', error);
        }
    };
    const handleRemoveFromBasket = (productId) => {
        setBasket(basket.filter(item => item.id !== productId));
    };

    const handleSubmitOrder = () => {
        const orderData = {
            productIds: basket.map(item => item.id),
            totalAmount: basket.reduce((sum, item) => sum + item.price * item.quantity, 0)
        };

        axios.post('http://localhost:8080/api/commands', orderData)
            .then(response => {
                console.log('Order placed:', response.data);
                setBasket([]);
                setIsBasketVisible(false);
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
            <Text style={styles.header}>Our Products</Text>
            <TextInput
                style={styles.searchBar}
                placeholder="Search for products"
                value={searchQuery}
                onChangeText={handleSearchChange}
            />

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
                            onPress={() => handleAddToBasket(item, 1)} // Default quantity of 1
                        >
                            <Image style={styles.cartIconSmall} />
                        </TouchableOpacity>
                    </View>
                )}
            />
            {isBasketVisible && (
                <View style={styles.basketModal}>
                    <FlatList
                        data={basket}
                        keyExtractor={(item) => item.id.toString()}
                        renderItem={({ item }) => (
                            <View style={styles.basketItem}>
                                <Text>{item.label} x{item.quantity}</Text>
                                <TouchableOpacity onPress={() => handleRemoveFromBasket(item.id)}>
                                    <Text style={styles.removeText}>Remove</Text>
                                </TouchableOpacity>
                            </View>
                        )}
                    />
                    <Button title="Submit Order" onPress={handleSubmitOrder} />
                    <Button title="Close" onPress={handleToggleBasket} />
                </View>
            )}

            {/* Floating View Basket Button */}
            <TouchableOpacity
                style={styles.viewBasketButton}
                onPress={handleToggleBasket}
            >
                <Image source={{uri: 'cart-icon-url'}} style={styles.cartIconLarge} />
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    header: {
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 20,
        textAlign: 'center',
    },
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: '#f9f9f9',
        marginTop: 50,
    },
    searchBar: {
        height: 40,
        borderColor: 'gray',
        borderWidth: 1,
        marginBottom: 10,
        borderRadius: 10,
        paddingLeft: 8,
    },
    loadingContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    loadingText: {
        marginTop: 10,
    },
    productCard: {
        width: '48%',
        marginBottom: 20,
        padding: 10,
        backgroundColor: '#f9f9f9',
        borderRadius: 10,
        borderColor: 'black',
        position: 'relative',
    },
    productImage: {
        width: '100%',
        height: 150,
        borderRadius: 10,
    },
    productName: {
        fontSize: 16,
        fontWeight: 'bold',
        marginVertical: 5,
        textAlign: 'center',
    },
    productPrice: {
        fontSize: 14,
        marginBottom: 10,
        textAlign: 'center',
    },
    addToBasketButton: {
        position: 'absolute',
        top: 10,
        right: 10,
        backgroundColor: '#3498db',
        padding: 8,
        borderRadius: 20,
        alignItems: 'center',
        justifyContent: 'center',
    },
    cartIconSmall: {
        width: 20,
        height: 20,
    },
    basketButton: {
        position: 'absolute',
        top: 10,
        right: 10,
        backgroundColor: '#3498db',
        padding: 10,
        borderRadius: 30,
    },
    basketModal: {
        position: 'absolute',
        top: 50,
        left: 20,
        right: 20,
        backgroundColor: '#fff',
        padding: 20,
        borderRadius: 10,
        maxHeight: '70%',
    },
    basketItem: {
        marginBottom: 10,
    },
    removeText: {
        color: 'red',
        textDecorationLine: 'underline',
    },
    modalOverlay: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(0,0,0,0.5)',
    },
    viewBasketButton: {
        position: 'absolute',
        bottom: 20,
        right: 20,
        backgroundColor: '#3498db',
        padding: 15,
        borderRadius: 50,
        alignItems: 'center',
        justifyContent: 'center',
    },
    cartIconLarge: {
        width: 30,
        height: 30,
    },
});

export default ProductListScreen;
