import React, { createContext, useState, useContext, useEffect } from 'react';

const ThemeContext = createContext(null);

export const ThemeProvider = ({ children }) => {
	// State to hold the current theme. Default to 'light'.
	const [theme, setTheme] = useState('light');

	// On initial load, check for a saved theme in localStorage or user's system preference.
	useEffect(() => {
		const savedTheme = localStorage.getItem('theme');
		const prefersDark = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;

		if (savedTheme) {
			setTheme(savedTheme);
		} else if (prefersDark) {
			setTheme('dark');
		}
	}, []);

	// Whenever the theme changes, save it to localStorage.
	useEffect(() => {
		localStorage.setItem('theme', theme);
	}, [theme]);

	const toggleTheme = () => {
		setTheme((prevTheme) => (prevTheme === 'light' ? 'dark' : 'light'));
	};

	return (
		<ThemeContext.Provider value={{ theme, toggleTheme }}>
			{children}
		</ThemeContext.Provider>
	);
};

export const useTheme = () => useContext(ThemeContext);
