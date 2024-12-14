import React from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, ImageBackground, Image } from 'react-native';
import { Formik } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';

const LoginScreen = ({ navigation }) => {
  const loginValidationSchema = Yup.object().shape({
    email: Yup.string().email('Email is invalid').required('Email is required'),
    password: Yup.string().min(8, 'Password must be at least 8 characters').required('Password is required'),
  });

  const handleLogin = async (values) => {
    try {
      console.log(values);  // Debugging line to inspect the values object
      const response = await axios.post("http://127.0.0.1:8080/comptes/loginii", values);
      const { message, token, type } = response.data;

      if (type === "client") {
        navigation.navigate("Home");
      } else if (type === "admin") {
        navigation.navigate("gusers");
      }
      else if (type === "livreur") {
        navigation.navigate("commandes");
      } else {
        alert('Invalid role');
      }
    } catch (error) {
      alert('Login failed: ' + (error.response?.data || error.message));
    }
  };


  return (
      <ImageBackground source={require('./background-image.jpg')} style={styles.background}>
        <View style={styles.container}>
          <Text style={styles.title}>Welcome Back</Text>
          <Image source={require('./LOGO.png')} style={styles.image} />
          <Formik
              initialValues={{ email: '', password: '' }}
              validationSchema={loginValidationSchema}
              onSubmit={handleLogin}
          >
            {({ handleChange, handleBlur, handleSubmit, values, errors, touched }) => (
                <>
                  <TextInput
                      placeholder="Email"
                      onChangeText={handleChange('email')}
                      onBlur={handleBlur('email')}
                      value={values.email}
                      style={styles.input}
                      autoCapitalize="none"
                  />
                  {errors.email && touched.email && <Text style={styles.error}>{errors.email}</Text>}
                  <TextInput
                      placeholder="Password"
                      secureTextEntry
                      onChangeText={handleChange('password')}
                      onBlur={handleBlur('password')}
                      value={values.password}
                      style={styles.input}
                  />
                  {errors.password && touched.password && <Text style={styles.error}>{errors.password}</Text>}
                  <TouchableOpacity style={styles.button} onPress={handleSubmit}>
                    <Text style={styles.buttonText}>Login</Text>
                  </TouchableOpacity>
                  <TouchableOpacity style={styles.signUpButton} onPress={() => navigation.navigate('SignUp')}>
                    <Text style={styles.signUpButtonText}>Don't have an account? Sign Up</Text>
                  </TouchableOpacity>
                  <TouchableOpacity style={styles.signUpButton} onPress={() => navigation.navigate('SignUpp')}>
                    <Text style={styles.signUpButtonText}>Want to be a deliveryman? Sign Up here</Text>
                  </TouchableOpacity>
                </>
            )}
          </Formik>
        </View>
      </ImageBackground>
  );
};

const styles = StyleSheet.create({
  background: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  container: {
    backgroundColor: 'rgba(255, 255, 255, 0.9)',
    padding: 24,
    borderRadius: 12,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.3,
    shadowRadius: 4,
    elevation: 5,
    width: '90%',
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
  },
  image: {
    width: 100,
    height: 100,
    marginLeft: 120,
    marginBottom: 20,
    borderRadius: 50,
  },
  input: {
    height: 50,
    borderColor: '#ccc',
    borderWidth: 1,
    borderRadius: 8,
    marginBottom: 10,
    paddingHorizontal: 16,
    backgroundColor: '#f9f9f9',
  },
  error: {
    color: 'red',
    marginBottom: 10,
  },
  button: {
    backgroundColor: '#4CAF50',
    borderRadius: 8,
    paddingVertical: 15,
    marginVertical: 10,
  },
  buttonText: {
    color: '#fff',
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 16,
  },
  signUpButtonText: {
    color: '#4CAF50',
    fontSize: 16,
    textDecorationLine: 'underline',
  },
});

export default LoginScreen;
