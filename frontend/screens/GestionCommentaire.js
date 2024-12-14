import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, TextInput } from 'react-native';
import axios from 'axios';
import Ionicons from 'react-native-vector-icons/Ionicons';
import { useNavigation } from '@react-navigation/native'; // Import the hook

const GestionCommentaires = () => {
  const [isMenuVisible, setMenuVisible] = useState(false);
  const [comments, setComments] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');

  const navigation = useNavigation(); // Use the hook to access navigation

  useEffect(() => {
    fetchComments();
  }, []);

  const fetchComments = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/comments');
      console.log('Comments fetched:', response.data); // Log the response data
      setComments(response.data);
    } catch (error) {
      console.error('Error fetching comments:', error);
    }
  };

  const deleteComment = async (commentId) => {
    try {
      // Sending DELETE request to the server
      await axios.delete(`http://localhost:8080/api/comments/admin/comments/${commentId}`);
      console.log('Comment deleted:', commentId);

      // Update state to remove the deleted comment
      setComments((prevComments) =>
          prevComments.filter((comment) => comment.id !== commentId)
      );
    } catch (error) {
      console.error('Error deleting comment:', error);
    }
  };

  const handleSearch = (text) => {
    setSearchQuery(text);
  };

  // Filtered comments based on search query
  const filteredComments = comments.filter((comment) =>
      comment.username?.toLowerCase().includes(searchQuery.toLowerCase()) // Use `username` directly
  );

  console.log('Filtered comments:', filteredComments); // Log filtered comments

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
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('gusers')}>
                <Text style={styles.menuText}>Gestion des utilisateurs</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Login')}>
                <Text style={styles.menuText}>Se déconnecter</Text>
              </TouchableOpacity>
            </View>
        )}
        <Text style={styles.header}>Gestion des Commentaires</Text>
        <TextInput
            style={styles.searchBar}
            placeholder="Rechercher par utilisateur"
            value={searchQuery}
            onChangeText={handleSearch}
        />
        <FlatList
            data={filteredComments}
            keyExtractor={(item) => item.id.toString()}
            renderItem={({ item }) => (
                <View style={styles.commentCard}>
                  <View style={styles.commentInfo}>
                    <Text style={styles.userName}>{item.username || 'Utilisateur inconnu'}</Text>
                    <Text style={styles.comment}>{item.content}</Text>
                    <Text style={styles.productName}>
                      Produit : {item.product ? item.product.label : 'N/A'}
                    </Text>
                    <TouchableOpacity
                        style={styles.deleteButton}
                        onPress={() => deleteComment(item.id)}
                    >
                      <Text style={styles.buttonText}>Supprimer</Text>
                    </TouchableOpacity>
                  </View>
                </View>
            )}
        />
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
  commentCard: {
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
  commentInfo: {
    flex: 1,
  },
  userName: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  comment: {
    fontSize: 14,
    color: '#555',
    marginVertical: 5,
  },
  productName: {
    fontSize: 14,
    fontStyle: 'italic',
    color: '#888',
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
});

export default GestionCommentaires;
