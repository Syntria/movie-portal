import React from 'react';
import { Layout, Dropdown, Menu, Space, Avatar, Typography } from 'antd';
import { DownOutlined, UserOutlined, VideoCameraOutlined, HeartOutlined, UnorderedListOutlined, LogoutOutlined } from '@ant-design/icons';
import { useAuth } from '../context/AuthContext';
import Link from 'next/link';

const { Header, Content, Footer } = Layout;

const UserLayout = ({ children }) => {
	const { user, logout } = useAuth();

	const menu = (
		<Menu>
			<Menu.Item key="1" icon={<VideoCameraOutlined />}>
				<Link href="/user/main">Main Page</Link>
			</Menu.Item>
			<Menu.Item key="2" icon={<HeartOutlined />}>
				<Link href="/user/favorites">Favorites</Link>
			</Menu.Item>
			<Menu.Item key="3" icon={<UnorderedListOutlined />}>
				<Link href="/user/watchlist">Watchlist</Link>
			</Menu.Item>
			<Menu.Divider />
			<Menu.Item key="4" icon={<LogoutOutlined />} onClick={logout}>
				Logout
			</Menu.Item>
		</Menu>
	);

	return (
		<Layout style={{ minHeight: '100vh' }}>
			<Header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', background: '#fff', boxShadow: '0 2px 8px #f0f1f2' }}>
				<Typography.Title level={4} style={{ margin: 0 }}>Movie Portal</Typography.Title>
				<Dropdown overlay={menu} trigger={['click']} placement="bottomRight">
					<a onClick={(e) => e.preventDefault()} href="!#">
						<Space>
							<Avatar icon={<UserOutlined />} />
							{user?.username}
							<DownOutlined />
						</Space>
					</a>
				</Dropdown>
			</Header>
			<Content style={{ padding: '24px 50px', marginTop: '24px' }}>
				{children}
			</Content>
			<Footer style={{ textAlign: 'center' }}>Movie Portal ©2025 Created with Ant Design</Footer>
		</Layout>
	);
};

export default UserLayout;
