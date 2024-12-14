import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, TextInput, ActivityIndicator, FlatList, TouchableOpacity, Image } from 'react-native';
import { Picker } from '@react-native-picker/picker';
import axios from 'axios';

const ProductListScreen = () => {
    const [categories, setCategories] = useState([]); // Liste des catégories
    const [products, setProducts] = useState([]); // Liste des produits
    const [filteredProducts, setFilteredProducts] = useState([]); // Produits filtrés
    const [selectedCategory, setSelectedCategory] = useState('all'); // Catégorie sélectionnée
    const [searchQuery, setSearchQuery] = useState(''); // Query pour la recherche
    const [loading, setLoading] = useState(true); // Indicateur de chargement

    // Récupération des catégories et produits depuis l'API
    useEffect(() => {
        // Fetch des catégories
        axios.get('http://localhost:8080/api/categories')
            .then(response => {
                setCategories(response.data);
            })
            .catch(error => {
                console.error('Erreur lors de la récupération des catégories', error);
            });

        // Fetch des produits
        axios.get('http://localhost:8080/api/products')
            .then(response => {
                setProducts(response.data);
                setFilteredProducts(response.data); // Affiche tous les produits initialement
                setLoading(false);
            })
            .catch(error => {
                console.error('Erreur lors de la récupération des produits', error);
                setLoading(false);
            });
    }, []);

    // Fonction de recherche des produits
    const handleSearchChange = (query) => {
        setSearchQuery(query);
        filterProducts(query, selectedCategory);
    };

    // Filtrer les produits selon la catégorie sélectionnée et la recherche
    const filterProducts = (query, category) => {
        let filtered = products;

        // Filtrer par catégorie
        if (category !== 'all') {
            filtered = filtered.filter(product => product.categorieId === category);
        }

        // Filtrer par recherche
        if (query) {
            filtered = filtered.filter(product =>
                product.label.toLowerCase().includes(query.toLowerCase())
            );
        }

        setFilteredProducts(filtered);
    };

    // Changer la catégorie sélectionnée
    const handleCategoryChange = (categoryId) => {
        setSelectedCategory(categoryId);
        filterProducts(searchQuery, categoryId); // Applique le filtrage en fonction de la catégorie et de la recherche
    };

    if (loading) {
        return (
            <View style={styles.loadingContainer}>
                <ActivityIndicator size="large" color="#3498db" />
                <Text style={styles.loadingText}>Chargement des produits...</Text>
            </View>
        );
    }

    return (
        <View style={styles.container}>
            {/* Search bar */}
            <TextInput
                style={styles.searchBar}
                placeholder="Rechercher un produit"
                value={searchQuery}
                onChangeText={handleSearchChange}
            />

            {/* Category picker */}
            <Picker
                selectedValue={selectedCategory}
                onValueChange={handleCategoryChange}
                style={styles.categoryPicker}
            >
                <Picker.Item label="Toutes les catégories" value="all" />
                {categories.map(category => (
                    <Picker.Item key={category.id} label={category.catname} value={category.id} />
                ))}
            </Picker>

            {/* Product list */}
            <FlatList
                data={filteredProducts}
                keyExtractor={(item) => item.id.toString()}
                numColumns={2}
                renderItem={({ item }) => (
                    <View style={styles.productCard}>
                        <Image
                            source={{ uri: item.photo }}
                            style={styles.productImage}
                            accessibilityLabel={`Image de ${item.label}`}
                        />
                        <Text style={styles.productName}>{item.label}</Text>
                        {/* Affichage de la catégorie du produit */}
                        <Text style={styles.productCategory}>
                            {categories.find(cat => cat.id === item.categorieId)?.catname || 'Aucune catégorie'}
                        </Text>
                        <Text style={styles.productPrice}>{item.price} MAD</Text>
                    </View>
                )}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        paddingHorizontal: 16,
        paddingTop: 40, // Augmenter la marge supérieure pour décaler le contenu vers le bas
        backgroundColor: '#ecf0f1',
    },
    loadingContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    loadingText: {
        fontSize: 18,
        color: '#3498db',
        marginTop: 10,
    },
    searchBar: {
        marginTop: 20,
        width: '100%',
        padding: 12,
        borderWidth: 1,
        borderColor: '#ddd',
        borderRadius: 25,
        marginBottom: 15,
        backgroundColor: '#fff',
        fontSize: 16,
        color: '#2c3e50',
    },
    categoryPicker: {
        width: '100%',
        backgroundColor: '#fff',
        borderRadius: 25,
        marginBottom: 20,
        borderWidth: 1,
        borderColor: '#ddd',
    },
    productCard: {
        width: '48%',
        backgroundColor: '#fff',
        borderRadius: 12,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 4 },
        shadowOpacity: 0.1,
        shadowRadius: 8,
        margin: '1%',
        padding: 12,
        alignItems: 'center',
    },
    productImage: {
        width: '100%',
        height: 140,
        borderRadius: 12,
        marginBottom: 10,
    },
    productName: {
        fontSize: 18,
        fontWeight: '600',
        textAlign: 'center',
        marginBottom: 5,
        color: '#34495e',
    },
    productCategory: {
        fontSize: 14,
        color: '#7f8c8d',
        textAlign: 'center',
        marginBottom: 10,
    },
    productPrice: {
        fontSize: 16,
        color: '#3498db',
        fontWeight: 'bold',
        textAlign: 'center',
    },
});

export default ProductListScreen;
