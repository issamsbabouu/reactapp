import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Image, FlatList, Modal, TextInput, ActivityIndicator } from 'react-native';
import Ionicons from 'react-native-vector-icons/Ionicons';
import axios from 'axios';

const HomeScreen = ({ navigation }) => {
  const [modalVisible, setModalVisible] = useState(false);
  const [selectedDish, setSelectedDish] = useState(null);
  const [cart, setCart] = useState([]);
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');

  useEffect(() => {
    axios.get('http://127.0.0.1:8080/api/products')
        .then(response => {
          setProducts(response.data);
          setLoading(false);
        })
        .catch(error => {
          console.error('Error fetching products:', error);
          setLoading(false);
        });
  }, []);

  const handleSearch = (text) => {
    setSearchQuery(text);
  };

  const filteredProducts = products.filter((product) =>
      product.label && product.label.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const openModal = (product) => {
    setSelectedDish(product);
    setModalVisible(true);
  };

  const closeModal = () => {
    setModalVisible(false);
    setSelectedDish(null);
  };

  const addToCart = () => {
    setCart((prevCart) => {
      const existingProduct = prevCart.find(item => item.id === selectedDish.id);
      if (existingProduct) {
        return prevCart;
      }
      return [...prevCart, selectedDish];
    });
    alert(`${selectedDish.label} a été ajouté au panier!`);
  };

  if (loading) {
    return (
        <View style={styles.loadingContainer}>
          <ActivityIndicator size="large" color="#4CAF50" />
          <Text>Chargement des produits...</Text>
        </View>
    );
  }

  return (
      <View style={styles.container}>
        <TextInput
            style={styles.searchBar}
            placeholder="Rechercher par nom"
            value={searchQuery}
            onChangeText={handleSearch}
        />

        <Text style={styles.header}>Meilleures ventes</Text>

        <FlatList
            data={filteredProducts}
            keyExtractor={(item) => item.id.toString()}
            numColumns={2}
            renderItem={({ item }) => (
                <View style={styles.dishCard}>
                  <TouchableOpacity onPress={() => openModal(item)}>
                    <Image
                        source={{ uri: item.photo }}
                        style={styles.dishImage}
                        accessibilityLabel={`Image de ${item.label}`}
                    />
                    <Text style={styles.dishName}>{item.label}</Text>
                    <Text style={styles.dishPrice}>{item.price} MAD</Text>
                  </TouchableOpacity>
                  <TouchableOpacity
                      style={styles.moreButton}
                      onPress={() => openModal(item)}
                      accessibilityLabel={`Plus de détails sur ${item.label}`}
                  >
                    <Ionicons name="ellipsis-horizontal" size={20} color="#fff" />
                  </TouchableOpacity>
                </View>
            )}
        />

        <View style={styles.cartContainer}>
          {cart.length > 0 ? (
              <>
                <Text style={styles.cartTitle}>Panier</Text>
                <FlatList
                    horizontal
                    data={cart.slice(0, 3)}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={({ item }) => (
                        <View style={styles.cartItem}>
                          <Image source={{ uri: item.photo }} style={styles.cartItemImage} />
                          <Text style={styles.cartItemName}>{item.label}</Text>
                        </View>
                    )}
                />
                <TouchableOpacity
                    style={styles.viewMoreButton}
                    onPress={() => navigation.navigate('CartScreen', { cart })}
                >
                  <Text style={styles.viewMoreText}>Voir plus</Text>
                </TouchableOpacity>
              </>
          ) : (
              <Text style={styles.emptyCartText}>Le panier est vide</Text>
          )}
        </View>

        {/* Modal for product details */}
        {selectedDish && (
            <Modal
                animationType="slide"
                transparent={true}
                visible={modalVisible}
                onRequestClose={closeModal}
            >
              <View style={styles.modalOverlay}>
                <View style={styles.modalView}>
                  <TouchableOpacity style={styles.closeButton} onPress={closeModal}>
                    <Ionicons name="close" size={24} color="#4CAF50" />
                  </TouchableOpacity>
                  <Image
                      source={{ uri: selectedDish.photo }}
                      style={styles.modalImage}
                      accessibilityLabel={`Image de ${selectedDish.label}`}
                  />
                  <Text style={styles.modalTitle}>{selectedDish.label}</Text>
                  <Text style={styles.modalPrice}>{selectedDish.price} MAD</Text>
                  <Text style={styles.modalDetails}>{selectedDish.description}</Text>
                  <TouchableOpacity style={styles.addToCartButton} onPress={addToCart}>
                    <Text style={styles.addToCartText}>Ajouter au panier</Text>
                  </TouchableOpacity>
                </View>
              </View>
            </Modal>
        )}
      </View>
  );
};
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'flex-start',
    alignItems: 'center',
    backgroundColor: 'transparent',
    padding: 16,
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
  },
  header: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#4CAF50',
    marginVertical: 20,
  },
  menuContainer: {
    alignItems: 'center',
    paddingBottom: 20,
  },
  grid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    width: '100%',
  },
  dishCard: {
    width: '48%',
    backgroundColor: '#fff',
    borderRadius: 12,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
    marginVertical: 10,
    padding: 10,
  },
  dishImage: {
    width: '100%',
    height: 150,
    borderRadius: 12,
    marginBottom: 10,
  },
  dishName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#4CAF50',
    textAlign: 'center',
  },
  dishPrice: {
    fontSize: 16,
    color: '#333',
    textAlign: 'center',
    marginVertical: 5,
  },
  moreButton: {
    backgroundColor: '#4CAF50',
    borderRadius: 20,
    padding: 8,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 10,
  },
  modalOverlay: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalView: {
    margin: 20,
    backgroundColor: 'white',
    borderRadius: 20,
    padding: 15,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  modalImage: {
    width: '100%',
    height: 250,
    borderRadius: 20,
    marginBottom: 10,
  },
  modalTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 5,
    color: '#4CAF50',
  },
  modalPrice: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 10,
    color: '#333',
  },
  modalDetails: {
    fontSize: 16,
    marginTop: 20,
    color: '#555',
    textAlign: 'center',
  },
  closeButton: {
    position: 'absolute',
    top: 10,
    right: 10,
  },
  addToCartButton: {
    backgroundColor: '#4CAF50',
    borderRadius: 20,
    padding: 10,
    marginTop: 15,
  },
  addToCartText: {
    color: '#fff',
    fontSize: 16,
  },
  cartContainer: {
    position: 'absolute',
    bottom: 10,
    width: '100%',
    backgroundColor: 'rgba(255, 255, 255, 0.8)',
    padding: 10,
    borderRadius: 10,
    alignItems: 'center',
  },
  cartTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#4CAF50',
  },
  cartScrollView: {
    flexDirection: 'row',
    marginVertical: 10,
  },
  cartItem: {
    marginHorizontal: 10,
    alignItems: 'center',
  },
  cartItemImage: {
    width: 50,
    height: 50,
    borderRadius: 10,
  },
  cartItemName: {
    fontSize: 14,
    color: '#333',
    textAlign: 'center',
  },
  viewMoreButton: {
    backgroundColor: '#4CAF50',
    paddingVertical: 5,
    paddingHorizontal: 20,
    borderRadius: 20,
  },
  viewMoreText: {
    color: '#fff',
    fontSize: 16,
  },
  emptyCartText: {
    fontSize: 16,
    color: '#999',
  },
});

export default HomeScreen;
