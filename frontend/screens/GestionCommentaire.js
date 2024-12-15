import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, TextInput } from 'react-native';
import axios from 'axios';

const MesCommentaires = () => {
  const [comments, setComments] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');

  useEffect(() => {
    fetchComments();
  }, []);

  const fetchComments = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/comments');
      console.log('Comments fetched:', response.data);
      setComments(response.data);
    } catch (error) {
      console.error('Error fetching comments:', error);
    }
  };

  const deleteComment = async (commentId) => {
    try {
      await axios.delete(`http://localhost:8080/api/comments/admin/comments/${commentId}`);
      console.log('Comment deleted:', commentId);
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
      comment.username?.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
      <View style={styles.container}>
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

                    <Text style={styles.productName}>
                      Produit : {item.product?.label || 'N/A'}
                    </Text>
                    <Text style={styles.comment}>{item.content}</Text>
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

export default MesCommentaires;
