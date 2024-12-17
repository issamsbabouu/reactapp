import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, TextInput, Image, ActivityIndicator } from 'react-native';
import axios from 'axios';
import Ionicons from "react-native-vector-icons/Ionicons";  // For the menu icon

const BasketPage = ({ navigation }) => {
    const [basket, setBasket] = useState([]);  // Initialize basket as an empty array
    const [searchQuery, setSearchQuery] = useState('');
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [isMenuVisible, setMenuVisible] = useState(false);  // For menu visibility

    useEffect(() => {
        fetchBasket();
    }, []);

    // Fetch basket data from the server
    const fetchBasket = async () => {
        try {
            setLoading(true);
            setError(null);

            const response = await axios.get('http://127.0.0.1:8080/api/panier/user', {
                withCredentials: true // Include session cookies
            });

            console.log('Fetched Basket:', response.data);
            if (response.data && Array.isArray(response.data.commandes)) {
                setBasket(response.data.commandes);  // Use setBasket instead of setOrders
            } else {
                setBasket([]);
            }
        } catch (error) {
            setError('Failed to fetch orders. Please try again later.');
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = (text) => {
        setSearchQuery(text);
    };

    // Filter basket items based on search query
    const filteredBasket = basket.filter((item) =>
        item.label?.toLowerCase().includes(searchQuery.toLowerCase())
    );

    // Handle product removal
    const deleteProduct = async (productId) => {
        try {
            await axios.delete(`http://127.0.0.1:8080/api/panier/remove/${productId}`, {
                withCredentials: true
            });
            console.log('Product removed:', productId);

            // Remove the product from the basket state
            setBasket(basket.filter(item => item.id !== productId));
        } catch (error) {
            console.error('Error removing product:', error.response ? error.response.data : error);
        }
    };

    const confirmOrder = async () => {
        try {
            const orderData = basket.map(item => {
                console.log('Item being processed for order:', item);

                // Use id instead of productId
                const id = item.id || null;

                if (!id) {
                    console.log('ID missing for item:', item);
                }

                return {
                    id: id,  // Pass id instead of productId
                    quantity: item.quantity
                };
            });

            if (orderData.some(order => order.id === null)) {
                throw new Error('ID is missing for some items');
            }

            const response = await axios.post('http://127.0.0.1:8080/api/orders/confirm', orderData, {
                withCredentials: true
            });

            console.log('Order confirmed:', response.data);
            alert('Order confirmed!');
        } catch (error) {
            console.error('Error confirming order:', error.response ? error.response.data : error);
            alert('Error confirming order');
        }
    };

    const clearBasket = async () => {
        try {
            await axios.post('http://127.0.0.1:8080/api/panier/clear', {}, {
                withCredentials: true
            });
            console.log('Basket cleared from backend');
        } catch (error) {
            console.error('Error clearing basket on backend:', error.response ? error.response.data : error);
        }
    };

    const handleMenuToggle = () => {
        setMenuVisible(!isMenuVisible);
    };

    return (
        <View style={styles.container}>
            {/* Menu Button */}
            <TouchableOpacity onPress={handleMenuToggle} style={styles.menuButton}>
                <Ionicons name="menu" size={30} color="black" />
            </TouchableOpacity>

            {/* Menu Visible */}
            {isMenuVisible && (
                <View style={styles.menu}>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Home')}>
                        <Text style={styles.menuText}>Catalog</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Settingsapp')}>
                        <Text style={styles.menuText}>Settings</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('myord')}>
                        <Text style={styles.menuText}>My orders</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('mycom')}>
                        <Text style={styles.menuText}>My comments</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Login')}>
                        <Text style={styles.menuText}>Logout</Text>
                    </TouchableOpacity>
                </View>
            )}

            <Text style={styles.header}>My Basket</Text>
            <TextInput
                style={styles.searchBar}
                placeholder="Search products"
                value={searchQuery}
                onChangeText={handleSearch}
            />
            {loading ? (
                <ActivityIndicator size="large" color="#4CAF50" style={styles.loader} />
            ) : error ? (
                <Text style={styles.errorText}>{error}</Text>
            ) : filteredBasket.length === 0 ? (
                <Text style={styles.noOrdersText}>No products found in your basket.</Text>
            ) : (
                <FlatList
                    data={filteredBasket}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={({ item }) => (
                        <View style={styles.productCard}>
                            {item.photo && (
                                <Image source={{ uri: item.photo }} style={styles.productImage} />
                            )}
                            <View style={styles.productInfo}>
                                <Text style={styles.productName}>{item.label || 'No Name'}</Text>
                                <Text style={styles.productPrice}>MAD{item.price || 'N/A'}</Text>
                                <Text style={styles.productQuantity}>Quantity: {item.quantity}</Text>
                                <TouchableOpacity
                                    style={styles.deleteButton}
                                    onPress={() => deleteProduct(item.id)}
                                >
                                    <Text style={styles.buttonText}>Remove</Text>
                                </TouchableOpacity>
                            </View>
                        </View>
                    )}
                />
            )}
            <TouchableOpacity
                style={styles.confirmButton}
                onPress={confirmOrder}
            >
                <Text style={styles.buttonText}>Confirm Order</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: '#f9f9f9',
        marginTop: 50,
    },
    header: {
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 20,
        textAlign: 'center',
    },
    searchBar: {
        height: 40,
        borderColor: '#ccc',
        borderWidth: 1,
        borderRadius: 8,
        paddingLeft: 10,
        marginBottom: 20,
    },
    productCard: {
        backgroundColor: '#fff',
        padding: 15,
        marginBottom: 15,
        borderRadius: 10,
        flexDirection: 'row',
        alignItems: 'center',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 5,
        elevation: 3,
    },
    productImage: {
        width: 80,
        height: 80,
        borderRadius: 8,
    },
    productInfo: {
        flex: 1,
        marginLeft: 10,
    },
    productName: {
        fontSize: 16,
        fontWeight: 'bold',
    },
    productPrice: {
        fontSize: 14,
        color: '#888',
    },
    productQuantity: {
        fontSize: 14,
        marginVertical: 5,
    },
    deleteButton: {
        backgroundColor: '#FF6347',
        padding: 5,
        borderRadius: 5,
        marginTop: 10,
    },
    buttonText: {
        color: '#fff',
        fontSize: 14,
    },
    confirmButton: {
        backgroundColor: '#4CAF50',
        padding: 10,
        borderRadius: 5,
        marginTop: 20,
        alignItems: 'center',
    },
    menuButton: {
        position: 'absolute',
        top: 20,
        left: 20,
        backgroundColor: '#fff',
        padding: 10,
        borderRadius: 30,
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
});

export default BasketPage;
