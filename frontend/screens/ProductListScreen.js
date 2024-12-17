import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, TextInput, ActivityIndicator, FlatList, TouchableOpacity, Image, Modal, Button } from 'react-native';
import axios from 'axios';
import Ionicons from "react-native-vector-icons/Ionicons";  // For the menu icon

const ProductListScreen = ({ navigation }) => {
    const [products, setProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);
    const [searchQuery, setSearchQuery] = useState('');
    const [loading, setLoading] = useState(true);
    const [basket, setBasket] = useState([]);
    const [isBasketVisible, setIsBasketVisible] = useState(false);
    const [authToken, setAuthToken] = useState('');
    const [isMenuVisible, setMenuVisible] = useState(false);  // For menu visibility

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
            const requestBody = {
                productId: product.id,
                quantity: quantity
            };

            const response = await axios.post('http://127.0.0.1:8080/api/panier/add', requestBody, {
                headers: {
                    'Content-Type': 'application/json',
                },
                withCredentials: true
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
            setBasket(response.data.commandes);
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

    const handleMenuToggle = () => {
        setMenuVisible(!isMenuVisible);
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
            {/* Menu Button */}
            <TouchableOpacity onPress={handleMenuToggle} style={styles.menuButton}>
                <Ionicons name="menu" size={30} color="black" />
            </TouchableOpacity>

            {/* Menu Visible */}
            {isMenuVisible && (
                <View style={styles.menu}>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('myord')}>
                        <Text style={styles.menuText}>My orders</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('basket')}>
                        <Text style={styles.menuText}>My basket</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('mycom')}>
                        <Text style={styles.menuText}>My comments</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Settingsapp')}>
                        <Text style={styles.menuText}>Settings</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Login')}>
                        <Text style={styles.menuText}>Logout</Text>
                    </TouchableOpacity>
                </View>
            )}

            {/* Product List */}
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
                            onPress={() => handleAddToBasket(item, 1)}
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
                <Image source={{ uri: 'cart-icon-url' }} style={styles.cartIconLarge} />
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
    menu: {
        position: 'absolute',
        top: 60,
        left: 20,
        backgroundColor: '#fff',
        padding: 10,
        borderRadius: 8,
        shadowColor: '#000',
        shadowOpacity: 0.2,
        shadowRadius: 5,
        elevation: 3,
        minWidth: 200,
        zIndex: 1000,
    },
    menuItem: {
        paddingVertical: 15,
        paddingHorizontal: 20,
        borderBottomWidth: 1,
        borderColor: '#ddd',
    },
    menuText: {
        fontSize: 16,
        color: '#333',
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
