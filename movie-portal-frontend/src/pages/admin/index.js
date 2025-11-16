import React, { useState, useEffect, useCallback } from 'react';
import {
	Layout, Table, Button, Modal, Form, Input, message,
	Popconfirm, Typography, Tabs, Space, Pagination, Spin, Image, Tooltip
} from 'antd';
import {
	PlusOutlined, DeleteOutlined, EditOutlined, UserOutlined,
	VideoCameraOutlined, CloudDownloadOutlined
} from '@ant-design/icons';
import apiClient from '../../lib/api';
import { useAuth } from '../../context/AuthContext';
import Head from 'next/head';

const { Header, Content, Footer } = Layout;
const { Title } = Typography;
const { Search } = Input;

const AdminPanel = () => {
	const [portalMovies, setPortalMovies] = useState([]);
	const [portalMoviePagination, setPortalMoviePagination] = useState({ current: 1, pageSize: 10, total: 0 });
	const [isMovieEditModalVisible, setIsMovieEditModalVisible] = useState(false);
	const [editingMovie, setEditingMovie] = useState(null);
	const [movieForm] = Form.useForm();
	const [tmdbMovies, setTmdbMovies] = useState([]);
	const [tmdbPagination, setTmdbPagination] = useState({ current: 1, pageSize: 20, total: 0 });
	const [tmdbSearchQuery, setTmdbSearchQuery] = useState('');
	const [isTmdbLoading, setIsTmdbLoading] = useState(false);
	const [portalTmdbIds, setPortalTmdbIds] = useState(new Set());
	const [users, setUsers] = useState([]);
	const [userPagination, setUserPagination] = useState({ current: 1, pageSize: 10, total: 0 });
	const [isUserAddModalVisible, setIsUserAddModalVisible] = useState(false);
	const [isUserEditModalVisible, setIsUserEditModalVisible] = useState(false);
	const [editingUser, setEditingUser] = useState(null);
	const [userForm] = Form.useForm();
	const [activeTabKey, setActiveTabKey] = useState('1');
	const { logout } = useAuth();

	const fetchPortalMovies = useCallback(async (page = 1, pageSize = 10) => {
		try {
			const response = await apiClient.get('/movies', { params: { page: page - 1, size: pageSize } });
			setPortalMovies(response.data.content);
			setPortalMoviePagination({
				current: response.data.number + 1,
				pageSize: response.data.size,
				total: response.data.totalElements,
			});
		} catch (error) {
			message.error('Failed to fetch portal movies.');
		}
	}, []);

	const fetchPortalTmdbIds = useCallback(async () => {
		try {
			const response = await apiClient.get('/movies/tmdb-ids');
			setPortalTmdbIds(new Set(response.data));
		} catch (error) {
			message.error('Could not verify which movies are in the portal.');
		}
	}, []);

	const fetchTmdbMovies = useCallback(async (page = 1) => {
		setIsTmdbLoading(true);
		try {
			const endpoint = tmdbSearchQuery ? '/tmdb/search' : '/tmdb/popular';
			const params = tmdbSearchQuery ? { query: tmdbSearchQuery, page } : { page };
			const response = await apiClient.get(endpoint, { params });
			setTmdbMovies(response.data.results);
			setTmdbPagination({
				current: response.data.page,
				pageSize: 20,
				total: response.data.total_results,
			});
		} catch (error) {
			message.error(`Failed to fetch movies from TMDB: ${error.message}`);
		} finally {
			setIsTmdbLoading(false);
		}
	}, [tmdbSearchQuery]);

	const fetchUsers = useCallback(async (page = 1, pageSize = 10) => {
		try {
			const response = await apiClient.get('/users', { params: { page: page - 1, size: pageSize } });
			const usersData = response.data.content.filter(u => u.username !== 'admin');
			setUsers(usersData);
			setUserPagination({
				current: response.data.number + 1,
				pageSize: response.data.size,
				total: response.data.totalElements,
			});
		} catch (error) {
			message.error('Failed to fetch users.');
		}
	}, []);

	useEffect(() => {
		fetchPortalMovies();
		fetchUsers();
		fetchTmdbMovies();
	}, []);

	useEffect(() => {
		if (activeTabKey === '1') {
			fetchPortalMovies(portalMoviePagination.current, portalMoviePagination.pageSize);
		} else if (activeTabKey === '2') {
			fetchPortalTmdbIds();
		} else if (activeTabKey === '3') {
			fetchUsers(userPagination.current, userPagination.pageSize);
		}
	}, [activeTabKey]);

	useEffect(() => {
		if (tmdbSearchQuery) {
			fetchTmdbMovies(1);
		}
	}, [tmdbSearchQuery]);

	const showEditMovieModal = (movie) => {
		setEditingMovie(movie);
		movieForm.setFieldsValue(movie);
		setIsMovieEditModalVisible(true);
	};

	const handleMovieModalCancel = () => {
		setIsMovieEditModalVisible(false);
		setEditingMovie(null);
	};

	const handleUpdateMovie = async (values, movieId) => {
		try {
			await apiClient.put(`/movies/${movieId}`, values);
			message.success('Movie updated successfully!');
			handleMovieModalCancel();
			fetchPortalMovies(portalMoviePagination.current, portalMoviePagination.pageSize);
		} catch (error) {
			message.error('Failed to update movie.');
		}
	};

	const handleDeletePortalMovie = async (movieId) => {
		try {
			await apiClient.delete(`/movies/${movieId}`);
			message.success('Movie deleted from portal!');
			fetchPortalMovies(portalMoviePagination.current, portalMoviePagination.pageSize);
			fetchPortalTmdbIds();
		} catch (error) {
			message.error('Failed to delete movie from portal.');
		}
	};

	const handleAddMovieFromTmdb = async (tmdbId) => {
		try {
			await apiClient.post(`/movies/tmdb/${tmdbId}`);
			message.success('Movie successfully added to portal!');
			setPortalTmdbIds(prev => new Set(prev).add(tmdbId));
		} catch (error) {
			message.error('Failed to add movie. It might already exist in the portal.');
		}
	};

	const handleRemoveMovieFromTmdb = async (tmdbId) => {
		try {
			await apiClient.delete(`/movies/tmdb/${tmdbId}`);
			message.success('Movie successfully removed from portal!');
			setPortalTmdbIds(prev => {
				const newSet = new Set(prev);
				newSet.delete(tmdbId);
				return newSet;
			});
		} catch (error) {
			message.error('Failed to remove movie from portal.');
		}
	};

	const handleTmdbSearch = (value) => {
		setTmdbSearchQuery(value);
	};

	const showAddUserModal = () => {
		userForm.resetFields();
		setIsUserAddModalVisible(true);
	};

	const showEditUserModal = (user) => {
		setEditingUser(user);
		userForm.setFieldsValue({ username: user.username, password: '' });
		setIsUserEditModalVisible(true);
	};

	const handleUserModalCancel = () => {
		setIsUserAddModalVisible(false);
		setIsUserEditModalVisible(false);
		setEditingUser(null);
	};

	const handleAddUser = async (values) => {
		try {
			await apiClient.post('/users', values);
			message.success('User created successfully!');
			setIsUserAddModalVisible(false);
			fetchUsers(1, userPagination.pageSize);
		} catch (error) {
			message.error('Failed to create user. Username might already exist.');
		}
	};

	const handleUpdateUser = async (values, userId) => {
		try {
			const updatePayload = {
				username: values.username,
				...(values.password && { password: values.password }),
			};
			await apiClient.put(`/users/${userId}`, updatePayload);
			message.success('User updated successfully!');
			handleUserModalCancel();
			fetchUsers(userPagination.current, userPagination.pageSize);
		} catch (error) {
			message.error('Failed to update user.');
		}
	};

	const handleDeleteUser = async (userId) => {
		try {
			await apiClient.delete(`/users/${userId}`);
			message.success('User deleted successfully!');
			fetchUsers(userPagination.current, userPagination.pageSize);
		} catch (error) {
			message.error('Failed to delete user.');
		}
	};

	const portalMovieColumns = [
		{
			title: 'Poster',
			dataIndex: 'posterPath',
			key: 'poster',
			render: (posterPath) => {
				const imageUrl = `https://image.tmdb.org/t/p/w200${posterPath}`;
				return posterPath ? <Image width={50} src={imageUrl} alt="poster" /> : <span>No Image</span>;
			},
		},
		{ title: 'Title', dataIndex: 'originalTitle', key: 'title', render: (text) => <Tooltip title={text}>{text}</Tooltip> },
		{
			title: 'Director',
			dataIndex: 'director',
			key: 'director',
			render: (director) => (director ? director.name : 'N/A'),
		},
		{ title: 'Release Date', dataIndex: 'releaseDate', key: 'releaseDate' },
		{
			title: 'Action', key: 'action',
			render: (_, record) => (
				<Space>
					<Button icon={<EditOutlined />} onClick={() => showEditMovieModal(record)}>Edit</Button>
					<Popconfirm title="Delete this movie?" onConfirm={() => handleDeletePortalMovie(record.id)}>
						<Button type="primary" danger icon={<DeleteOutlined />}>Delete</Button>
					</Popconfirm>
				</Space>
			),
		},
	];

	const tmdbMovieColumns = [
		{
			title: 'Poster',
			dataIndex: 'poster_path',
			key: 'poster',
			render: (posterPath) => {
				const imageUrl = `https://image.tmdb.org/t/p/w200${posterPath}`;
				return posterPath ? <Image width={50} src={imageUrl} alt="poster" /> : <span>No Image</span>;
			},
		},
		{ title: 'Title', dataIndex: 'title', key: 'title', render: (text) => <Tooltip title={text}>{text}</Tooltip> },
		{ title: 'Release Date', dataIndex: 'release_date', key: 'releaseDate' },
		{
			title: 'Action', key: 'action',
			render: (_, record) => {
				const isInPortal = portalTmdbIds.has(record.id);
				return isInPortal ? (
					<Popconfirm
						title="Remove this movie from your portal?"
						onConfirm={() => handleRemoveMovieFromTmdb(record.id)}
						okText="Yes, Remove"
						cancelText="No"
					>
						<Button type="primary" danger icon={<DeleteOutlined />}>Remove from Portal</Button>
					</Popconfirm>
				) : (
					<Button
						type="primary"
						icon={<PlusOutlined />}
						onClick={() => handleAddMovieFromTmdb(record.id)}
					>
						Add to Portal
					</Button>
				);
			},
		},
	];

	const userColumns = [
		{ title: 'ID', dataIndex: 'id', key: 'id' },
		{ title: 'Username', dataIndex: 'username', key: 'username' },
		{
			title: 'Action', key: 'action',
			render: (_, record) => (
				<Space>
					<Button icon={<EditOutlined />} onClick={() => showEditUserModal(record)}>Edit</Button>
					<Popconfirm title="Delete this user permanently?" onConfirm={() => handleDeleteUser(record.id)}>
						<Button type="primary" danger icon={<DeleteOutlined />}>Delete</Button>
					</Popconfirm>
				</Space>
			),
		},
	];

	const tabItems = [
		{
			key: '1',
			label: <span><VideoCameraOutlined /> Manage Portal Movies</span>,
			children: (
				<>
					<Table columns={portalMovieColumns} dataSource={portalMovies} rowKey="id" pagination={false} />
					<Pagination {...portalMoviePagination} onChange={fetchPortalMovies} style={{ marginTop: 16, textAlign: 'center' }} />
				</>
			),
		},
		{
			key: '2',
			label: <span><CloudDownloadOutlined /> Add Movies from TMDB</span>,
			children: (
				<>
					<Search
						placeholder="Search for movies on TMDB..."
						onSearch={handleTmdbSearch}
						enterButton
						style={{ marginBottom: 16 }}
					/>
					{isTmdbLoading ? (
						<div style={{ textAlign: 'center', padding: '50px' }}><Spin size="large" /></div>
					) : (
						<>
							<Table columns={tmdbMovieColumns} dataSource={tmdbMovies} rowKey="id" pagination={false} />
							<Pagination
								current={tmdbPagination.current}
								pageSize={tmdbPagination.pageSize}
								total={tmdbPagination.total}
								onChange={fetchTmdbMovies}
								style={{ marginTop: 16, textAlign: 'center' }}
								showSizeChanger={false}
							/>
						</>
					)}
				</>
			),
		},
		{
			key: '3',
			label: <span><UserOutlined /> Manage Users</span>,
			children: (
				<>
					<Button type="primary" icon={<PlusOutlined />} onClick={showAddUserModal} style={{ marginBottom: 16 }}>
						Add User
					</Button>
					<Table columns={userColumns} dataSource={users} rowKey="id" pagination={false} />
					<Pagination {...userPagination} onChange={fetchUsers} style={{ marginTop: 16, textAlign: 'center' }} />
				</>
			),
		},
	];

	return (
		<>
			<Head><title>Admin Panel</title></Head>
			<Layout style={{ minHeight: '100vh' }}>
				<Header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
					<Title level={3} style={{ color: 'white', margin: 0 }}>Admin Panel</Title>
					<Button onClick={logout}>Logout</Button>
				</Header>
				<Content style={{ padding: '24px 50px' }}>
					<Tabs
						activeKey={activeTabKey}
						onChange={setActiveTabKey}
						type="card"
						items={tabItems}
					/>
				</Content>
				<Footer style={{ textAlign: 'center' }}>Movie Portal ©2025</Footer>
				{editingMovie && (
					<Modal title="Edit Movie" open={isMovieEditModalVisible} onCancel={handleMovieModalCancel} footer={null}>
						<Form
							form={movieForm}
							layout="vertical"
							onFinish={(values) => handleUpdateMovie(values, editingMovie.id)}
							initialValues={editingMovie}
						>
							<Form.Item name="originalTitle" label="Title" rules={[{ required: true }]}><Input /></Form.Item>
							<Form.Item name="overview" label="Overview" rules={[{ required: true }]}><Input.TextArea rows={4} /></Form.Item>
							<Form.Item name="posterPath" label="Poster Path (from TMDB)" rules={[{ required: true }]}><Input /></Form.Item>
							<Form.Item name="releaseDate" label="Release Date" rules={[{ required: true }]}><Input placeholder="YYYY-MM-DD" /></Form.Item>
							<Button type="primary" htmlType="submit">Update Movie</Button>
						</Form>
					</Modal>
				)}
				<Modal title="Add New User" open={isUserAddModalVisible} onCancel={handleUserModalCancel} footer={null}>
					<Form form={userForm} layout="vertical" onFinish={handleAddUser}>
						<Form.Item name="username" label="Username" rules={[{ required: true }]}><Input /></Form.Item>
						<Form.Item name="password" label="Password" rules={[{ required: true }]}><Input.Password /></Form.Item>
						<Button type="primary" htmlType="submit">Create User</Button>
					</Form>
				</Modal>
				{editingUser && (
					<Modal title="Edit User" open={isUserEditModalVisible} onCancel={handleUserModalCancel} footer={null}>
						<Form
							form={userForm}
							layout="vertical"
							onFinish={(values) => handleUpdateUser(values, editingUser.id)}
							initialValues={{ username: editingUser.username }}
						>
							<Form.Item name="username" label="Username" rules={[{ required: true }]}><Input /></Form.Item>
							<Form.Item name="password" label="New Password (optional)">
								<Input.Password placeholder="Leave blank to keep current password" />
							</Form.Item>
							<Button type="primary" htmlType="submit">Update User</Button>
						</Form>
					</Modal>
				)}
			</Layout>
		</>
	);
};

export default AdminPanel;
