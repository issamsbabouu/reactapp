import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Image } from 'react-native';
import { LineChart, BarChart, PieChart } from 'react-native-chart-kit';
import { Dimensions } from 'react-native';
import Ionicons from 'react-native-vector-icons/Ionicons';

const screenWidth = Dimensions.get('window').width;

const Dashboard = ({ navigation }) => {
  const [isMenuVisible, setMenuVisible] = useState(false);

  const toggleMenu = () => {
    setMenuVisible(!isMenuVisible);
  };

  // Example of statistical data
  const salesData = [50, 70, 80, 90, 100, 110, 120];
  const productsSold = [25, 35, 45, 55, 65, 75, 85];
  const categoriesData = [
    {
      name: 'Pizza',
      population: 215000,
      color: '#f00',
      legendFontColor: '#000',
      legendFontSize: 15,
    },
    {
      name: 'Sushi',
      population: 250000,
      color: '#00f',
      legendFontColor: '#000',
      legendFontSize: 15,
    },
    {
      name: 'Burgers',
      population: 100000,
      color: '#0f0',
      legendFontColor: '#000',
      legendFontSize: 15,
    },
    {
      name: 'Pâtes',
      population: 75000,
      color: '#ff0',
      legendFontColor: '#000',
      legendFontSize: 15,
    },
  ];

  return (
      <ScrollView contentContainerStyle={styles.container}>
        <TouchableOpacity onPress={toggleMenu} style={styles.menuButton}>
          <Ionicons name="menu" size={30} color="black" />
        </TouchableOpacity>

        {isMenuVisible && (
            <View style={styles.menu}>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Gestioncateg')}>
                <Text style={styles.menuText}>Gestion des catégories</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Gestionprods')}>
                <Text style={styles.menuText}>Gestion des produits</Text>
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

        <Text style={styles.header}>Dashboard</Text>

        {/* Sales chart */}
        <Text style={styles.sectionHeader}>Ventes (par mois)</Text>
        <LineChart
            data={{
              labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'],
              datasets: [
                {
                  data: salesData,
                },
              ],
            }}
            width={screenWidth - 40}
            height={220}
            chartConfig={{
              backgroundColor: '#ffffff',
              backgroundGradientFrom: '#ffffff',
              backgroundGradientTo: '#ffffff',
              decimalPlaces: 0,
              color: (opacity = 1) => `rgba(0, 0, 255, ${opacity})`,
              labelColor: (opacity = 1) => `rgba(0, 0, 0, ${opacity})`,
              style: {
                borderRadius: 16,
              },
              propsForDots: {
                r: '6',
                strokeWidth: '2',
                stroke: '#ffa726',
              },
            }}
            style={styles.chart}
        />

        {/* Products sold chart */}
        <Text style={styles.sectionHeader}>Produits vendus</Text>
        <BarChart
            data={{
              labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'],
              datasets: [
                {
                  data: productsSold,
                },
              ],
            }}
            width={screenWidth - 40}
            height={220}
            chartConfig={{
              backgroundColor: '#ffffff',
              backgroundGradientFrom: '#ffffff',
              backgroundGradientTo: '#ffffff',
              decimalPlaces: 0,
              color: (opacity = 1) => `rgba(0, 0, 255, ${opacity})`,
              labelColor: (opacity = 1) => `rgba(0, 0, 0, ${opacity})`,
              style: {
                borderRadius: 16,
              },
            }}
            style={styles.chart}
        />

        {/* Categories distribution chart */}
        <Text style={styles.sectionHeader}>Répartition des catégories</Text>
        <PieChart
            data={categoriesData}
            width={screenWidth - 40}
            height={220}
            chartConfig={{
              backgroundColor: '#ffffff',
              backgroundGradientFrom: '#ffffff',
              backgroundGradientTo: '#ffffff',
              color: (opacity = 1) => `rgba(0, 0, 255, ${opacity})`,
              labelColor: (opacity = 1) => `rgba(0, 0, 0, ${opacity})`,
            }}
            accessor="population"
            backgroundColor="transparent"
            paddingLeft="15"
            absolute
        />

      </ScrollView>
  );
};
const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    padding: 20,
    backgroundColor: '#f9f9f9',
    marginTop:50,
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
  sectionHeader: {
    fontSize: 18,
    fontWeight: 'bold',
    marginVertical: 10,
  },
  chart: {
    marginVertical: 20,
    borderRadius: 16,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 5,
    elevation: 3,
  },
});

export default Dashboard;
