import React, { useState } from 'react'; // 1. Import useState
import { Form, Input, Button, Card, Typography, Alert } from 'antd'; // 2. Import Alert
import { UserOutlined, LockOutlined, VideoCameraTwoTone } from '@ant-design/icons';
import { useAuth } from '../context/AuthContext';
import apiClient from '../lib/api';

const { Title } = Typography;

const LoginPage = () => {
	const { login } = useAuth();
	const [loading, setLoading] = useState(false);
	const [errorMessage, setErrorMessage] = useState('');

	const onFinish = async (values) => {
		setLoading(true);
		setErrorMessage(''); // Clear previous errors
		try {
			const params = new URLSearchParams();
			params.append('username', values.username);
			params.append('password', values.password);

			const response = await apiClient.post('/users/login', params, {
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			});

			login(response.data);
		} catch (error) {
			console.error("Login attempt failed:", error.response);

			if (error.response && error.response.status === 401) {
				setErrorMessage('Wrong username or password. Please try again.');
			} else {
				setErrorMessage('Login failed. Could not connect to the server.');
			}
		} finally {
			setLoading(false);
		}
	};

	return (
		<div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh' }}>
			<Card style={{ width: 400, boxShadow: '0 4px 20px 0 rgba(0, 0, 0, 0.1)' }}>
				<div style={{ textAlign: 'center', marginBottom: '24px' }}>
					<VideoCameraTwoTone twoToneColor="#1890ff" style={{ fontSize: '48px' }} />
					<Title level={2}>Movie Portal</Title>
				</div>
				<Form name="login" onFinish={onFinish} size="large">
					<Form.Item name="username" rules={[{ required: true, message: 'Please input your Username!' }]}>
						<Input prefix={<UserOutlined />} placeholder="Username" />
					</Form.Item>
					<Form.Item name="password" rules={[{ required: true, message: 'Please input your Password!' }]}>
						<Input.Password prefix={<LockOutlined />} placeholder="Password" />
					</Form.Item>

					{}
					{errorMessage && (
						<Form.Item>
							<Alert message={errorMessage} type="error" showIcon />
						</Form.Item>
					)}

					<Form.Item>
						<Button type="primary" htmlType="submit" style={{ width: '100%' }} loading={loading}>
							Log In
						</Button>
					</Form.Item>
				</Form>
			</Card>
		</div>
	);
};

export default LoginPage;
