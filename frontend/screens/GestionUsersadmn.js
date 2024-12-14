import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, Modal, TextInput, Button } from 'react-native';
import Ionicons from 'react-native-vector-icons/Ionicons';
import axios from 'axios';

const GestionUsers = ({ navigation }) => {
  const [isMenuVisible, setMenuVisible] = useState(false);
  const [users, setUsers] = useState([]); // Initialise users à un tableau vide
  const [searchQuery, setSearchQuery] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [newUser, setNewUser] = useState({
    username: '',
    email: '',
    phone: '',
    password: '',
    type: '',
    photo: '',
    nom: '',
    filiere: ''
  });
  const [selectedUser, setSelectedUser] = useState(null);

  const API_URL = 'http://localhost:8080/comptes';

  // Charger les utilisateurs depuis l'API au chargement de la page
  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await axios.get(API_URL);
      setUsers(response.data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  const handleSearch = (text) => {
    setSearchQuery(text);
  };

  // Filtrer les utilisateurs en fonction de la recherche
  const filteredUsers = users.filter((user) =>
      user.nom && user.nom.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const handleAddUser = async () => {
    if (newUser.username && newUser.email) {
      try {
        const response = await axios.post(API_URL, newUser);
        setNewUser({
          username: '',
          email: '',
          phone: '',
          password: '',
          type: '',
          photo: '',
          nom: '',
          filiere: ''
        });
        setModalVisible(false);
        fetchUsers();
      } catch (error) {
        console.error('Error adding user:', error);
      }
    }
  };

  const handleDeleteUser = async (userId) => {
    try {
      await axios.delete(`${API_URL}/${userId}`);
      fetchUsers(); // Rafraîchir la liste après la suppression
    } catch (error) {
      console.error('Error deleting user:', error);
    }
  };

  return (
      <View style={styles.container}>
        <TouchableOpacity onPress={() => setMenuVisible(!isMenuVisible)} style={styles.menuButton}>
          <Ionicons name="menu" size={30} color="black" />
        </TouchableOpacity>
        {isMenuVisible && (
            <View style={styles.menu}>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('dashboard')}>
                <Text style={styles.menuText}>Dashboard</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Gestioncateg')}>
                <Text style={styles.menuText}>Gestion des catégories</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Gestionprods')}>
                <Text style={styles.menuText}>Gestion des produits</Text>
              </TouchableOpacity>

              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('gestioncomment')}>
                <Text style={styles.menuText}>Commentaires et FeedBacks</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Login')}>
                <Text style={styles.menuText}>Se déconnecter</Text>
              </TouchableOpacity>
            </View>
        )}

        <Text style={styles.header}>Gestion des Utilisateurs</Text>

        {/* Search Bar */}
        <TextInput
            style={styles.searchBar}
            placeholder="Rechercher par nom"
            value={searchQuery}
            onChangeText={handleSearch}
        />

        <FlatList
            data={filteredUsers}
            keyExtractor={(item) => item.id.toString()}
            renderItem={({ item }) => (
                <View style={styles.userCard}>
                  <Text style={styles.userName}>Nom d'utilsateur: {item.nom}</Text>
                  <Text style={styles.userEmail}>Adresse mail: {item.email}</Text>
                  <Text style={styles.userEmail}>Type: {item.type}</Text>
                  <TouchableOpacity style={styles.deleteButton} onPress={() => handleDeleteUser(item.id)}>
                    <Text style={styles.buttonText}>Supprimer</Text>
                  </TouchableOpacity>
                </View>
            )}
        />

        {/* Add/Edit User Modal */}
        <Modal visible={modalVisible} onRequestClose={() => setModalVisible(false)} animationType="slide">
          <View style={styles.modalContainer}>
            <Text style={styles.modalHeader}>{selectedUser ? 'Modifier l\'utilisateur' : 'Ajouter un utilisateur'}</Text>

            <TextInput
                style={styles.input}
                placeholder="Nom"
                value={selectedUser ? selectedUser.nom : newUser.nom}
                onChangeText={(text) => selectedUser ? setSelectedUser({ ...selectedUser, nom: text }) : setNewUser({ ...newUser, nom: text })}
            />

            <TextInput
                style={styles.input}
                placeholder="Email"
                value={selectedUser ? selectedUser.email : newUser.email}
                onChangeText={(text) => selectedUser ? setSelectedUser({ ...selectedUser, email: text }) : setNewUser({ ...newUser, email: text })}
            />

            <Button
                title={selectedUser ? 'Sauvegarder' : 'Ajouter'}
                onPress={selectedUser ? handleEditUser : handleAddUser}
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
  addButton: {
    backgroundColor: '#4CAF50',
    padding: 10,
    borderRadius: 8,
    marginBottom: 20,
    alignItems: 'center',
  },
  addText: {
    color: '#fff',
    fontSize: 16,
  },
  userCard: {
    backgroundColor: '#fff',
    padding: 15,
    marginBottom: 15,
    borderRadius: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 5,
    elevation: 3,
  },
  userName: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  userEmail: {
    fontSize: 14,
    color: '#888',
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

export default GestionUsers;
