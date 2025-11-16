import React, { createContext, useState, useContext, useEffect } from 'react';
import { useRouter } from 'next/router';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
	const [user, setUser] = useState(null);
	const [loading, setLoading] = useState(true);
	const router = useRouter();

	useEffect(() => {
		// Check for user in localStorage on initial load
		const storedUser = localStorage.getItem('user');
		if (storedUser) {
			setUser(JSON.parse(storedUser));
		}
		setLoading(false);
	}, []);

	const login = (userData) => {
		localStorage.setItem('user', JSON.stringify(userData));
		setUser(userData);
		if (userData.username === 'admin') {
			router.push('/admin');
		} else {
			router.push('/user/main');
		}
	};

	const logout = () => {
		localStorage.removeItem('user');
		setUser(null);
		router.push('/login');
	};

	return (
		<AuthContext.Provider value={{ user, login, logout, loading }}>
			{children}
		</AuthContext.Provider>
	);
};

export const useAuth = () => useContext(AuthContext);
