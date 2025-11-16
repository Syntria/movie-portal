import { AuthProvider } from '../context/AuthContext';
import '../styles/globals.css';
import 'antd/dist/reset.css';

function MyApp({ Component, pageProps }) {
	return (
		<AuthProvider>
			<Component {...pageProps} />
		</AuthProvider>
	);
}

export default MyApp;
