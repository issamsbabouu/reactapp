import React, { createContext, useState } from 'react';
export const CartContext = createContext();
export const CartProvider = ({ children }) => {
    const [cartItems, setCartItems] = useState([]);
    const addToCart = (item) => {
        setCartItems((prevItems) => [...prevItems, item]);
    };
    return (
        <CartProvider>
            <NavigationContainer>
                <Stack.Navigator initialRouteName="ProductList">
                    <Stack.Screen name="ProductList" component={ProductListScreen} />
                    <Stack.Screen name="ProductDetail" component={ProductDetailScreen} />
                </Stack.Navigator>
            </NavigationContainer>
        </CartProvider>
    );
};
