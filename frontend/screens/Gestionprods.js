import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, Modal, TextInput, Button, ScrollView } from 'react-native';
import Ionicons from 'react-native-vector-icons/Ionicons';
import axios from 'axios';
import { Picker } from '@react-native-picker/picker'; // Correct import

const GestionProduit = ({ navigation }) => {
    const [isMenuVisible, setMenuVisible] = useState(false);
    const [produits, setProduits] = useState([]);  // Liste des produits
    const [categories, setCategories] = useState([]); // Liste des catégories
    const [searchQuery, setSearchQuery] = useState('');
    const [modalVisible, setModalVisible] = useState(false);
    const [isEditing, setIsEditing] = useState(false); // State to check if it's edit mode
    const [selectedProduit, setSelectedProduit] = useState(null); // Store the selected product for editing

    const [newProduit, setNewProduit] = useState({
        label: '',
        description: '',
        price: '',
        color: '',
        photo: '',
        size: '',
        quantity: '',
        categorieId: null
    });

    const API_URL_PRODUCTS = 'http://localhost:8080/api/products';
    const API_URL_CATEGORIES = 'http://localhost:8080/api/categories';

    // Charger les produits et catégories depuis l'API au chargement de la page
    useEffect(() => {
        fetchProduits();
        fetchCategories();
    }, []);

    const fetchProduits = async () => {
        try {
            const response = await axios.get(API_URL_PRODUCTS);
            if (Array.isArray(response.data)) {
                setProduits(response.data);  // Mettre à jour la liste des produits
            }
        } catch (error) {
            console.error('Erreur lors de la récupération des produits:', error);
            setProduits([]);
        }
    };

    const fetchCategories = async () => {
        try {
            const response = await axios.get(API_URL_CATEGORIES);
            if (Array.isArray(response.data)) {
                setCategories(response.data);  // Mettre à jour la liste des catégories
            }
        } catch (error) {
            console.error('Erreur lors de la récupération des catégories:', error);
        }
    };

    const handleSearch = (text) => {
        setSearchQuery(text);
    };

    const filteredProduits = produits.filter((produit) =>
        produit.label && produit.label.toLowerCase().includes(searchQuery.toLowerCase())
    );

    const handleAddProduit = async () => {
        if (newProduit.label && newProduit.price && newProduit.quantity && newProduit.categorieId) {
            try {
                if (isEditing) {
                    await axios.put(`${API_URL_PRODUCTS}/${selectedProduit.id}`, newProduit);
                    setIsEditing(false);
                    setSelectedProduit(null);
                } else {
                    await axios.post(API_URL_PRODUCTS, newProduit);
                }
                setNewProduit({
                    label: '',
                    description: '',
                    price: '',
                    color: '',
                    photo: '',
                    size: '',
                    quantity: '',
                    categorieId: ''
                });
                setModalVisible(false);
                fetchProduits();
            } catch (error) {
                console.error('Erreur lors de l\'ajout ou modification du produit:', error);
                alert('Une erreur est survenue. Vérifiez les données envoyées ou contactez l\'administrateur du serveur.');
            }
        } else {
            alert("Veuillez remplir tous les champs obligatoires.");
        }
    };

    const handleEditProduit = (produit) => {
        setNewProduit(produit);
        setSelectedProduit(produit);
        setIsEditing(true);
        setModalVisible(true);
    };

    const handleDeleteProduit = async (produitId) => {
        try {
            await axios.delete(`${API_URL_PRODUCTS}/${produitId}`);
            fetchProduits();
        } catch (error) {
            console.error('Erreur lors de la suppression du produit:', error);
        }
    };
    return (
        <View style={styles.container}>
            {/* Menu Button */}
            <TouchableOpacity onPress={() => setMenuVisible(!isMenuVisible)} style={styles.menuButton}>
                <Ionicons name="menu" size={30} color="black" />
            </TouchableOpacity>

            {/* Menu Visible */}
            {isMenuVisible && (
                <View style={styles.menu}>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Dashboard')}>
                        <Text style={styles.menuText}>Dashboard</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Gestioncateg')}>
                        <Text style={styles.menuText}>Gestion des catégories</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('gusers')}>
                        <Text style={styles.menuText}>Gestion des utilisateurs</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('gestioncomment')}>
                        <Text style={styles.menuText}>Gestion des commentaires</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Login')}>
                        <Text style={styles.menuText}>Se déconnecter</Text>
                    </TouchableOpacity>
                </View>
            )}

            <Text style={styles.header}>Gestion des Produits</Text>

            {/* Search Bar */}
            <TextInput
                style={styles.searchBar}
                placeholder="Rechercher par nom"
                value={searchQuery}
                onChangeText={handleSearch}
            />

            {/* Add Product Button */}
            <TouchableOpacity style={styles.addProduitButton} onPress={() => setModalVisible(true)}>
                <Text style={styles.addProduitText}>Ajouter un produit</Text>
            </TouchableOpacity>

            <FlatList
                data={filteredProduits}
                keyExtractor={(item) => item.id.toString()}
                renderItem={({ item }) => (
                    <View style={styles.produitCard}>
                        <Text style={styles.produitLabel}>{item.label}</Text>
                        <Text>{item.categorieId} </Text>
                        <Text>{item.price} MAD</Text>
                        <TouchableOpacity
                            style={styles.editButton}
                            onPress={() => handleEditProduit(item)}>
                            <Text style={styles.buttonText}>Modifier</Text>
                        </TouchableOpacity>
                        <TouchableOpacity
                            style={styles.deleteButton}
                            onPress={() => handleDeleteProduit(item.id)}>
                            <Text style={styles.buttonText}>Supprimer</Text>
                        </TouchableOpacity>
                    </View>
                )}
            />

            {/* Add/Modify Product Modal */}
            <Modal visible={modalVisible} onRequestClose={() => setModalVisible(false)} animationType="slide">
                <View style={styles.modalContainer}>
                    <Text style={styles.modalHeader}>{isEditing ? 'Modifier le produit' : 'Ajouter un produit'}</Text>

                    <ScrollView contentContainerStyle={styles.scrollView}>
                        {/* Labels added for each input field */}
                        <Text style={styles.label}>Nom du produit</Text>
                        <TextInput
                            style={styles.input}
                            placeholder="Nom du produit"
                            value={newProduit.label}
                            onChangeText={(text) => setNewProduit({ ...newProduit, label: text })}
                        />
                        <Text style={styles.label}>Description</Text>
                        <TextInput
                            style={styles.input}
                            placeholder="Description"
                            value={newProduit.description}
                            onChangeText={(text) => setNewProduit({ ...newProduit, description: text })}
                        />
                        <Text style={styles.label}>Catégorie</Text>
                        <TextInput
                            style={styles.input}
                            placeholder="Description"
                            value={newProduit.categorieId}
                            onChangeText={(text) => setNewProduit({ ...newProduit, categorieId: text })}
                        />
                        <Text style={styles.label}>Prix</Text>
                        <TextInput
                            style={styles.input}
                            placeholder="Prix"
                            value={newProduit.price}
                            keyboardType="numeric"
                            onChangeText={(text) => setNewProduit({ ...newProduit, price: text })}
                        />
                        <Text style={styles.label}>Couleur</Text>
                        <TextInput
                            style={styles.input}
                            placeholder="Couleur"
                            value={newProduit.color}
                            onChangeText={(text) => setNewProduit({ ...newProduit, color: text })}
                        />
                        <Text style={styles.label}>Photo (URL)</Text>
                        <TextInput
                            style={styles.input}
                            placeholder="Photo (URL)"
                            value={newProduit.photo}
                            onChangeText={(text) => setNewProduit({ ...newProduit, photo: text })}
                        />
                        <Text style={styles.label}>Taille</Text>
                        <TextInput
                            style={styles.input}
                            placeholder="Taille"
                            value={newProduit.size}
                            onChangeText={(text) => setNewProduit({ ...newProduit, size: text })}
                        />
                        <Text style={styles.label}>Quantité</Text>
                        <TextInput
                            style={styles.input}
                            placeholder="Quantité"
                            value={newProduit.quantity}
                            keyboardType="numeric"
                            onChangeText={(text) => setNewProduit({ ...newProduit, quantity: text })}
                        />

                        {/* Category Picker */}
                        <Text style={styles.label}>Catégorie</Text>
                        <Picker
                            selectedValue={newProduit.categorieId}
                            onValueChange={(itemValue) =>
                                setNewProduit({ ...newProduit, categorieId: itemValue })
                            }
                        >
                            <Picker.Item label="Choisissez une catégorie" value={null} />
                            {categories.map((category) => (
                                <Picker.Item
                                    key={category.id}
                                    label={category.catname}
                                    value={category.id}
                                />
                            ))}
                        </Picker>

                        <View style={styles.modalButtons}>
                            <Button title="Annuler" onPress={() => setModalVisible(false)} />
                            <Button title={isEditing ? "Modifier" : "Ajouter"} onPress={handleAddProduit} />
                        </View>
                    </ScrollView>
                </View>
            </Modal>
        </View>
    );
};
const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: '#f5f5f5',
        marginTop: 40,
    },
    menuButton: {
        position: 'absolute',
        top: 20,
        left: 20,
        zIndex: 10,
        padding: 10,
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
    header: {
        fontSize: 24,
        fontWeight: 'bold',
        textAlign: 'center',
        marginVertical: 20,
    },
    searchBar: {
        height: 40,
        borderColor: '#ccc',
        borderWidth: 1,
        marginBottom: 20,
        paddingLeft: 10,
        borderRadius: 5,
    },
    addProduitButton: {
        backgroundColor: '#4CAF50',
        padding: 10,
        borderRadius: 5,
        marginBottom: 20,
    },
    addProduitText: {
        color: 'white',
        fontSize: 16,
        textAlign: 'center',
    },
    produitCard: {
        backgroundColor: 'white',
        padding: 15,
        marginVertical: 10,
        borderRadius: 5,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowOffset: { width: 0, height: 2 },
    },
    produitLabel: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    editButton: {
        backgroundColor: '#2196F3',
        padding: 5,
        marginTop: 10,
        borderRadius: 5,
        marginBottom: 5,
    },
    deleteButton: {
        backgroundColor: 'red',
        padding: 5,
        marginTop: 10,
        borderRadius: 5,
    },
    buttonText: {
        color: 'white',
        textAlign: 'center',
    },
    modalContainer: {
        flex: 1,
        padding: 20,
        justifyContent: 'flex-start',
    },
    scrollView: {
        paddingBottom: 20,
    },
    modalHeader: {
        fontSize: 20,
        fontWeight: 'bold',
        marginBottom: 20,
        textAlign: 'center',
    },
    label: {
        fontSize: 14,
        fontWeight: 'bold',
        marginBottom: 5,
        color: 'black',
    },
    input: {
        height: 40,
        borderColor: '#ccc',
        borderWidth: 1,
        marginBottom: 15,
        paddingLeft: 10,
        borderRadius: 5,
        color: 'black',
    },
    modalButtons: {
        marginTop: 20,
    },
});

export default GestionProduit;
