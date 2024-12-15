import React, { useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, FlatList, Modal } from 'react-native';


const settingsOptions = [
  { id: '1', title: 'Thème' },
  { id: '3', title: 'Confidentialité' },
  { id: '4', title: 'Langue' },
  { id: '5', title: 'À propos de l\'application' },
  { id: '6', title: 'Supprimer mon compte' },
];

const languageOptions = [
  { id: '1', title: 'Français' },
  { id: '2', title: 'Anglais' },
];

const aboutText = "Ceci est une application de démonstration. Elle vous permet de gérer vos paramètres, y compris la langue et le thème.";

const SettingsScreen = () => {
  const [modalVisible, setModalVisible] = useState(false);
  const [languageModalVisible, setLanguageModalVisible] = useState(false);
  const [aboutModalVisible, setAboutModalVisible] = useState(false);
  const [deleteModalVisible, setDeleteModalVisible] = useState(false);

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
    setDeleteModalVisible(true);
  };

  const handleDeleteConfirm = () => {
    console.log("Account deleted");
    setDeleteModalVisible(false);
  };

  const handleDeleteCancel = () => {
    setDeleteModalVisible(false);
  };

  const renderLanguageItem = ({ item }) => (
      <TouchableOpacity style={styles.languageOption}>
        <Text style={[styles.languageText, isDarkMode && styles.darkText]}>{item.title}</Text>
      </TouchableOpacity>
  );

  return (
      <View style={[styles.container, isDarkMode && styles.darkContainer]}>
        <Text style={[styles.header, isDarkMode && styles.darkHeader]}>Paramètres</Text>
        {settingsOptions.map(option => (
            <TouchableOpacity key={option.id} style={styles.option} onPress={() => {
              if (option.id === '1') handleThemePress();
              if (option.id === '4') handleLanguagePress();
              if (option.id === '5') handleAboutPress();
              if (option.id === '6') handleDeleteAccountPress();
            }}>
              <Text style={[styles.optionText, isDarkMode && styles.darkText]}>{option.title}</Text>
            </TouchableOpacity>
        ))}

        {/* Theme Modal */}
        <Modal transparent={true} visible={modalVisible} animationType="slide">
          <View style={styles.modalContainer}>
            <Text style={[styles.modalHeader, isDarkMode && styles.darkModalHeader]}>Choisir un thème</Text>
            <TouchableOpacity
                style={[styles.button, styles.lightButton]}
                onPress={() => setModalVisible(false)}
            >
              <Text style={styles.buttonText}>Clair</Text>
            </TouchableOpacity>
            <TouchableOpacity
                style={[styles.button, styles.darkButton]}
                onPress={toggleTheme} // Activate dark mode
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
            <Text style={[styles.modalHeader, isDarkMode && styles.darkModalHeader]}>Choisir une langue</Text>
            <FlatList
                data={languageOptions}
                renderItem={renderLanguageItem}
                keyExtractor={item => item.id}
                contentContainerStyle={styles.languageList}
            />
            <TouchableOpacity
                style={styles.closeButton}
                onPress={() => setLanguageModalVisible(false)}
            >
              <Text style={styles.closeButtonText}>Fermer</Text>
            </TouchableOpacity>
          </View>
        </Modal>

        {/* About Modal */}
        <Modal transparent={true} visible={aboutModalVisible} animationType="slide">
          <View style={styles.modalContainer}>
            <Text style={[styles.modalHeader, isDarkMode && styles.darkModalHeader]}>À propos de l'application</Text>
            <Text style={[styles.aboutText, isDarkMode && styles.darkAboutText]}>{aboutText}</Text>
            <TouchableOpacity
                style={styles.closeButton}
                onPress={() => setAboutModalVisible(false)}
            >
              <Text style={styles.closeButtonText}>Fermer</Text>
            </TouchableOpacity>
          </View>
        </Modal>

        {/* Delete Account Modal */}
        <Modal transparent={true} visible={deleteModalVisible} animationType="slide">
          <View style={styles.modalContainer}>
            <Text style={[styles.modalHeader, isDarkMode && styles.darkModalHeader]}>Êtes-vous sûr de vouloir supprimer votre compte ?</Text>
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
  darkContainer: {
    backgroundColor: '#333',
  },
  header: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  darkHeader: {
    color: 'white',
  },
  option: {
    padding: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
  },
  optionText: {
    fontSize: 18,
  },
  darkText: {
    color: 'white',
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.8)',
    padding: 20,
    borderRadius: 10,
    marginHorizontal: 40,
  },
  darkModalHeader: {
    color: 'white',
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
  darkAboutText: {
    color: 'white',
  },
});

export default SettingsScreen;
