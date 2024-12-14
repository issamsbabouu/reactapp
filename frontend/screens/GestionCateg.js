import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, Modal, TextInput, Button } from 'react-native';
import Ionicons from 'react-native-vector-icons/Ionicons';
import axios from 'axios';

const GestionCateg = ({ navigation }) => {
  const [isMenuVisible, setMenuVisible] = useState(false);
  const [categories, setCategories] = useState([]); // Initialise categories à un tableau vide
  const [searchQuery, setSearchQuery] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [newCategory, setNewCategory] = useState('');
  const [selectedCategory, setSelectedCategory] = useState(null);

  // URL de votre API Spring Boot (adaptez-le à votre configuration)
  const API_URL = 'http://localhost:8080/api/categories';

  // Charger les catégories depuis l'API au chargement de la page
  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const response = await axios.get(API_URL);
      // Vérifiez si la réponse est un tableau
      if (Array.isArray(response.data)) {
        setCategories(response.data); // Mettez à jour le state avec les catégories extraites
      } else {
        console.error('La réponse de l\'API n\'est pas un tableau valide', response.data);
      }
    } catch (error) {
      console.error('Error fetching categories:', error);
      setCategories([]); // Assurez-vous que categories est un tableau vide en cas d'erreur
    }
  };

  const toggleMenu = () => {
    setMenuVisible(!isMenuVisible);
  };

  const handleSearch = (text) => {
    setSearchQuery(text);
  };

  // Filtrer les catégories en fonction de la recherche
  const filteredCategories = categories.filter((category) =>
      category.catname && category.catname.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const handleAddCategory = async () => {
    if (newCategory) {
      try {
        const response = await axios.post(API_URL, { catname: newCategory });
        setNewCategory('');
        setModalVisible(false);
        fetchCategories(); // Rafraîchir la liste après l'ajout
      } catch (error) {
        console.error('Error adding category:', error);
      }
    }
  };

  const handleDeleteCategory = async (categoryId) => {
    try {
      await axios.delete(`${API_URL}/${categoryId}`);
      fetchCategories(); // Rafraîchir la liste après la suppression
    } catch (error) {
      console.error('Error deleting category:', error);
    }
  };

  return (
      <View style={styles.container}>
        <TouchableOpacity onPress={toggleMenu} style={styles.menuButton}>
          <Ionicons name="menu" size={30} color="black" />
        </TouchableOpacity>
        {isMenuVisible && (
            <View style={styles.menu}>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('dashboard')}>
                <Text style={styles.menuText}>Dashboard</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Gestionprods')}>
                <Text style={styles.menuText}>Gestion des produits</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('gusers')}>
                <Text style={styles.menuText}>Gestion des utilisateurs</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('gestioncomment')}>
                <Text style={styles.menuText}>Commentaires et FeedBacks</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Login')}>
                <Text style={styles.menuText}>Se déconnecter</Text>
              </TouchableOpacity>
            </View>
        )}

        <Text style={styles.header}>Gestion des Catégories</Text>

        {/* Search Bar */}
        <TextInput
            style={styles.searchBar}
            placeholder="Rechercher par nom"
            value={searchQuery}
            onChangeText={handleSearch}
        />

        {/* Add Category Button */}
        <TouchableOpacity style={styles.addCategoryButton} onPress={() => setModalVisible(true)}>
          <Text style={styles.addCategoryText}>Ajouter une catégorie</Text>
        </TouchableOpacity>

        <FlatList
            data={filteredCategories}
            keyExtractor={(item) => item.id.toString()}
            renderItem={({ item }) => (
                <View style={styles.categoryCard}>
                  <Text style={styles.categoryName}>{item.catname}</Text>
                  <TouchableOpacity
                      style={styles.deleteButton}
                      onPress={() => handleDeleteCategory(item.id)}>
                    <Text style={styles.buttonText}>Supprimer</Text>
                  </TouchableOpacity>
                </View>
            )}
        />

        {/* Add/Edit Category Modal */}
        <Modal visible={modalVisible} onRequestClose={() => setModalVisible(false)} animationType="slide">
          <View style={styles.modalContainer}>
            <Text style={styles.modalHeader}>{selectedCategory ? 'Modifier la catégorie' : 'Ajouter une catégorie'}</Text>

            <TextInput
                style={styles.input}
                placeholder="Nom de la catégorie"
                value={selectedCategory ? selectedCategory.catname : newCategory}
                onChangeText={(text) => selectedCategory ? setSelectedCategory({ ...selectedCategory, catname: text }) : setNewCategory(text)}
            />

            <Button
                title={selectedCategory ? 'Sauvegarder' : 'Ajouter'}
                onPress={selectedCategory ? handleEditCategory : handleAddCategory}
            />
            <Button title="Annuler" onPress={() => setModalVisible(false)} color="red" />
          </View>
        </Modal>
      </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    marginTop: 50,
    backgroundColor: '#f5f5f5',
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
  addCategoryButton: {
    backgroundColor: '#4CAF50',
    padding: 10,
    borderRadius: 8,
    marginBottom: 20,
    alignItems: 'center',
  },
  addCategoryText: {
    color: '#fff',
    fontSize: 16,
  },
  categoryCard: {
    backgroundColor: '#fff',
    padding: 15,
    marginBottom: 15,
    borderRadius: 10,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 5,
    elevation: 3,
  },
  categoryName: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  editButton: {
    backgroundColor: '#FFA500',
    padding: 5,
    borderRadius: 5,
  },
  deleteButton: {
    backgroundColor: '#FF6347',
    padding: 5,
    borderRadius: 5,
  },
  buttonText: {
    color: '#fff',
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    padding: 20,
    backgroundColor: '#fff',
  },
  modalHeader: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
  },
  input: {
    height: 40,
    borderColor: '#ccc',
    borderWidth: 1,
    borderRadius: 8,
    paddingLeft: 10,
    marginBottom: 20,
  },
});

export default GestionCateg;
