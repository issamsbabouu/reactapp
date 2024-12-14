import React, { useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, FlatList, Modal, ScrollView } from 'react-native';

const settingsOptions = [
  { id: '1', title: 'Thème' },
  { id: '3', title: 'Confidentialité' },
  { id: '4', title: 'Langue' },
  { id: '5', title: 'À propos de l\'application' },
  { id: '6', title: 'Supprimer mon compte' }, // Add the delete account option here
];

const languageOptions = [
  { id: '1', title: 'Français' },
  { id: '2', title: 'Anglais' },
];

const aboutText = "";

const SettingsScreen = () => {
  const [modalVisible, setModalVisible] = useState(false);
  const [languageModalVisible, setLanguageModalVisible] = useState(false);
  const [aboutModalVisible, setAboutModalVisible] = useState(false);
  const [deleteModalVisible, setDeleteModalVisible] = useState(false); // State for the delete confirmation modal

  const handleThemePress = () => {
    setModalVisible(true);
  };

  const handleLanguagePress = () => {
    setLanguageModalVisible(true);
  };

  const handleAboutPress = () => {
    setAboutModalVisible(true);
  };

  const handleDeleteAccountPress = () => {
    setDeleteModalVisible(true); // Show the confirmation modal when delete account is pressed
  };

  const handleDeleteConfirm = () => {
    // Add your logic to delete the account here
    console.log("Account deleted");
    setDeleteModalVisible(false); // Close the modal after confirmation
  };

  const handleDeleteCancel = () => {
    setDeleteModalVisible(false); // Close the modal without deleting
  };

  const renderItem = ({ item }) => (
    <TouchableOpacity
      style={[styles.option, item.title === 'Supprimer mon compte' && styles.deleteButton]} // Apply deleteButton style for the 'Delete Account' option
      onPress={
        item.title === 'Thème'
          ? handleThemePress
          : item.title === 'Langue'
          ? handleLanguagePress
          : item.title === 'À propos de l\'application'
          ? handleAboutPress
          : item.title === 'Supprimer mon compte'
          ? handleDeleteAccountPress
          : null
      }
    >
      <Text style={[styles.optionText, item.title === 'Supprimer mon compte' && styles.deleteButtonText]}>
        {item.title}
      </Text>
    </TouchableOpacity>
  );
  const renderLanguageItem = ({ item }) => (
    <TouchableOpacity style={styles.languageOption}>
      <Text style={styles.languageText}>{item.title}</Text>
    </TouchableOpacity>
  );

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Paramètres</Text>
      <FlatList
        data={settingsOptions}
        renderItem={renderItem}
        keyExtractor={item => item.id}
      />
      
      {/* Theme Modal */}
      <Modal transparent={true} visible={modalVisible} animationType="slide">
        <View style={styles.modalContainer}>
          <Text style={styles.modalHeader}>Choisir un thème</Text>
          <TouchableOpacity
            style={[styles.button, styles.lightButton]}
            onPress={() => setModalVisible(false)}
          >
            <Text style={styles.buttonText}>Clair</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.button, styles.darkButton]}
            onPress={() => setModalVisible(false)}
          >
            <Text style={styles.buttonText}>Sombre</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={styles.closeButton}
            onPress={() => setModalVisible(false)}
          >
            <Text style={styles.closeButtonText}>Fermer</Text>
          </TouchableOpacity>
        </View>
      </Modal>

      {/* Language Modal */}
      <Modal transparent={true} visible={languageModalVisible} animationType="slide">
        <View style={styles.modalContainer}>
          <Text style={styles.modalHeader}>Choisir une langue</Text>
          <ScrollView style={styles.languageList}>
            <FlatList
              data={languageOptions}
              renderItem={renderLanguageItem}
              keyExtractor={item => item.id}
            />
          </ScrollView>
          <TouchableOpacity
            style={styles.closeButton}
            onPress={() => setLanguageModalVisible(false)}
          >
            <Text style={styles.closeButtonText}>Fermer</Text>
          </TouchableOpacity>
        </View>
      </Modal>
      {}
      <Modal transparent={true} visible={aboutModalVisible} animationType="slide">
        <View style={styles.modalContainer}>
          <Text style={styles.modalHeader}>À propos de l'application</Text>
          <Text style={styles.aboutText}>{aboutText}</Text>
          <TouchableOpacity
            style={styles.closeButton}
            onPress={() => setAboutModalVisible(false)}
          >
            <Text style={styles.closeButtonText}>Fermer</Text>
          </TouchableOpacity>
        </View>
      </Modal>
      {}
      <Modal transparent={true} visible={deleteModalVisible} animationType="slide">
        <View style={styles.modalContainer}>
          <Text style={styles.modalHeader}>Êtes-vous sûr de vouloir supprimer votre compte ?</Text>
          <TouchableOpacity
            style={[styles.button, styles.lightButton]}
            onPress={handleDeleteConfirm}
          >
            <Text style={styles.buttonText}>Oui</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.button, styles.darkButton]}
            onPress={handleDeleteCancel}
          >
            <Text style={styles.buttonText}>Non</Text>
          </TouchableOpacity>
        </View>
      </Modal>
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
  },
  option: {
    padding: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
  },
  optionText: {
    fontSize: 18,
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.8)',
    padding: 20,
    marginTop: 300,
    marginBottom: 300,
    marginLeft: 50,
    marginRight: 50,
    borderRadius: 60,
  },
  deleteButtonText: {
    color: 'red', 
  },
  modalHeader: {
    fontSize: 24,
    marginBottom: 20,
    color: 'white',
    textAlign: 'center',
  },
  button: {
    padding: 15,
    marginVertical: 10,
    borderRadius: 5,
    width: '80%',
    alignItems: 'center',
  },
  lightButton: {
    backgroundColor: '#f0f0f0',
  },
  darkButton: {
    backgroundColor: '#333',
  },
  buttonText: {
    fontSize: 18,
    color: 'black',
  },
  closeButton: {
    marginTop: 20,
  },
  closeButtonText: {
    color: 'white',
    fontSize: 16,
  },
  languageList: {
    width: 150,
    paddingLeft: 20,
  },
  languageOption: {
    padding: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
    backgroundColor: 'transparent',
    width: '80%',
    alignItems: 'center',
    borderRadius: 9,
  },
  languageText: {
    fontSize: 18,
    color: 'white',
  },
  aboutText: {
    color: 'white',
    textAlign: 'justify',
    marginVertical: 20,
    paddingHorizontal: 10,
  },
});

export default SettingsScreen;
