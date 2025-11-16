import React, { useState, useEffect } from 'react';
import {
	List, Card, Button, message, Spin, Empty, Tooltip,
	Typography, Modal, Row, Col, Divider, Form, Input, Popconfirm, Avatar, Tag, Space
} from 'antd';
import {
	DeleteOutlined, HeartOutlined, EditOutlined, UserOutlined,
	ArrowLeftOutlined, ArrowRightOutlined
} from '@ant-design/icons';
import UserLayout from '../../components/UserLayout';
import apiClient from '../../lib/api';
import Head from 'next/head';
import Link from 'next/link';
import { useAuth } from '../../context/AuthContext';

const { Meta } = Card;
const { Paragraph, Text, Title } = Typography;

const FavoritesPage = () => {
	const { user } = useAuth();
	const [favorites, setFavorites] = useState([]);
	const [loading, setLoading] = useState(true);

	const [modalStack, setModalStack] = useState([]);
	const [modalIndex, setModalIndex] = useState(-1);
	const [modalDataLoading, setModalDataLoading] = useState(false);

	const [comments, setComments] = useState([]);
	const [commentsLoading, setCommentsLoading] = useState(false);
	const [commentForm] = Form.useForm();

	const fetchFavorites = () => {
		setLoading(true);
		apiClient.get('/favorites')
			.then(response => setFavorites(response.data))
			.catch(() => message.error('Could not fetch favorites.'))
			.finally(() => setLoading(false));
	};

	useEffect(fetchFavorites, []);

	const currentModalData = modalStack[modalIndex];

	const pushToModalStack = (newItem) => {
		const newStack = modalStack.slice(0, modalIndex + 1);
		newStack.push(newItem);
		setModalStack(newStack);
		setModalIndex(newStack.length - 1);
	};

	const handleShowMovie = async (movie) => {
		if (!movie || !movie.id) return;
		const isSimpleMovieObject = movie.cast === undefined;
		if (isSimpleMovieObject) {
			setModalDataLoading(true);
			try {
				const response = await apiClient.get(`/movies/${movie.id}`);
				const fullMovieData = response.data;
				pushToModalStack({ type: 'movie', data: fullMovieData });
				fetchComments(fullMovieData.id);
			} catch (error) {
				message.error("Could not fetch movie details.");
			} finally {
				setModalDataLoading(false);
			}
		} else {
			pushToModalStack({ type: 'movie', data: movie });
			fetchComments(movie.id);
		}
	};

	const handleShowDirector = async (director) => {
		if (!director || !director.id) return;
		setModalDataLoading(true);
		try {
			const response = await apiClient.get(`/directors/${director.id}`);
			pushToModalStack({ type: 'director', data: response.data });
		} catch {
			message.error("Could not fetch director's details.");
		} finally {
			setModalDataLoading(false);
		}
	};

	const handleModalClose = () => {
		setModalStack([]);
		setModalIndex(-1);
		setComments([]);
	};

	const goBack = () => {
		if (modalIndex > 0) {
			const previousItem = modalStack[modalIndex - 1];
			if (previousItem.type === 'movie') {
				fetchComments(previousItem.data.id);
			}
			setModalIndex(modalIndex - 1);
		}
	};

	const goForward = () => {
		if (modalIndex < modalStack.length - 1) {
			const nextItem = modalStack[modalIndex + 1];
			if (nextItem.type === 'movie') {
				fetchComments(nextItem.data.id);
			}
			setModalIndex(modalIndex + 1);
		}
	};

	const fetchComments = async (movieId) => {
		setCommentsLoading(true);
		try {
			const response = await apiClient.get(`/movies/${movieId}/comments`);
			setComments(response.data);
		} catch (error) {
			message.error("Could not load your note.");
		} finally {
			setCommentsLoading(false);
		}
	};

	const handleSaveComment = async (values) => {
		if (!values.text || values.text.trim() === '') return;
		try {
			await apiClient.post(`/movies/${currentModalData.data.id}/comments`, { text: values.text });
			message.success("Note saved!");
			fetchComments(currentModalData.data.id);
			commentForm.resetFields();
		} catch (error) {
			message.error("Could not save note.");
		}
	};

	const handleDeleteComment = async () => {
		try {
			await apiClient.delete(`/movies/${currentModalData.data.id}/comments/mine`);
			message.success("Note deleted!");
			fetchComments(currentModalData.data.id);
		} catch (error) {
			message.error("Could not delete note.");
		}
	};

	const handleRemove = async (movieId) => {
		try {
			await apiClient.delete(`/favorites/${movieId}`);
			message.success('Removed from favorites!');
			fetchFavorites();
		} catch (error) {
			message.error('Could not remove from favorites.');
		}
	};

	const renderMovieModalContent = (movie) => (
		<Row gutter={24}>
			<Col xs={24} sm={8}>
				<img alt={movie.originalTitle} src={movie.posterPath ? `https://image.tmdb.org/t/p/w500${movie.posterPath}` : 'https://placehold.co/300x450?text=No+Image'} style={{ width: '100%', borderRadius: '8px' }} />
			</Col>
			<Col xs={24} sm={16}>
				<Title level={4}>Overview</Title>
				<Paragraph>{movie.overview}</Paragraph>
				<Text strong>Release Date: </Text><Text>{movie.releaseDate}</Text><br />
				<Text strong>Director: </Text>
				<Button type="link" style={{ padding: '0' }} onClick={() => handleShowDirector(movie.director)}>{movie.director ? movie.director.name : 'N/A'}</Button>
				<Title level={5} style={{ marginTop: '16px' }}>Cast</Title>
				<List
					grid={{ gutter: 16, column: 3 }}
					dataSource={movie.cast?.slice(0, 6)}
					renderItem={(actor) => (
						<List.Item>
							<div style={{
								display: 'flex',
								flexDirection: 'column',
								alignItems: 'center',
								textAlign: 'center',
								padding: '8px',
								height: '100%',
								boxSizing: 'border-box'
							}}>
								<Avatar size={64} src={actor.profilePath ? `https://image.tmdb.org/t/p/w185${actor.profilePath}` : null} icon={<UserOutlined />} style={{ marginBottom: '8px' }} />

								<div style={{ height: '40px', display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
									<Text strong ellipsis={{ tooltip: actor.actorName || actor.name }}>
										{actor.actorName || actor.name}
									</Text>
									<Text type="secondary" ellipsis={{ tooltip: `as ${actor.characterName}` }}>
										as {actor.characterName}
									</Text>
								</div>
							</div>
						</List.Item>
					)}
				/>
				<Divider />
				<Title level={4}>My Notes</Title>
				{(() => {
					if (commentsLoading) return <Spin />;
					const myComment = comments.find(c => c.username === user.username);
					if (myComment) {
						return (
							<div>
								<Paragraph editable={{ icon: <EditOutlined />, tooltip: 'Edit your note', onChange: (text) => handleSaveComment({ text }) }}>{myComment.text}</Paragraph>
								<Popconfirm title="Delete your note?" onConfirm={handleDeleteComment}><Button type="link" danger icon={<DeleteOutlined />}>Delete</Button></Popconfirm>
							</div>
						);
					} else {
						return (
							<Form form={commentForm} onFinish={handleSaveComment}>
								<Form.Item name="text"><Input.TextArea rows={3} placeholder="Add your private note for this movie..." /></Form.Item>
								<Form.Item><Button htmlType="submit" type="primary">Save Note</Button></Form.Item>
							</Form>
						);
					}
				})()}
			</Col>
		</Row>
	);

	const renderDirectorModalContent = (director) => (
		<Row gutter={24}>
			<Col xs={24} sm={8}>
				<img alt={director.name} src={director.profilePath ? `https://image.tmdb.org/t/p/w500${director.profilePath}` : 'https://placehold.co/300x450?text=No+Image'} style={{ width: '100%', borderRadius: '8px' }} />
			</Col>
			<Col xs={24} sm={16}>
				<div style={{ minHeight: '450px' }}>
					<Title level={4}>Filmography in Portal</Title>
					<List
						dataSource={director.movies}
						renderItem={(movie) => (
							<List.Item>
								<List.Item.Meta
									avatar={<Avatar src={movie.posterPath ? `https://image.tmdb.org/t/p/w185${movie.posterPath}` : null} />}
									title={<Button type="link" style={{ padding: '0', textAlign: 'left', height: 'auto' }} onClick={() => handleShowMovie(movie)}>{movie.originalTitle}</Button>}
									description={movie.releaseDate}
								/>
							</List.Item>
						)}
					/>
				</div>
			</Col>
		</Row>
	);

	const modalTitle = (
		<div style={{ display: 'flex', alignItems: 'center' }}>
			<Space>
				<Button key="back" icon={<ArrowLeftOutlined />} onClick={goBack} disabled={modalIndex <= 0} />
				<Button key="forward" icon={<ArrowRightOutlined />} onClick={goForward} disabled={modalIndex >= modalStack.length - 1} />
			</Space>
			<Title level={4} style={{ marginLeft: '16px', marginBottom: 0, flexGrow: 1, overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
				{currentModalData?.type === 'movie' ? currentModalData.data.originalTitle : currentModalData?.data.name}
			</Title>
		</div>
	);

	return (
		<UserLayout>
			<Head><title>My Favorites</title></Head>
			<Title level={3} style={{ marginBottom: '24px' }}><HeartOutlined /> My Favorites</Title>
			{loading ? <div style={{ textAlign: 'center', marginTop: '50px' }}><Spin size="large" /></div> : (
				favorites.length > 0 ? (
					<List
						grid={{ gutter: 24, xs: 1, sm: 2, md: 3, lg: 4, xl: 4, xxl: 5 }}
						dataSource={favorites}
						renderItem={(fav) => (
							<List.Item>
								<Card
									hoverable
									style={{ width: '100%' }}
									cover={<img
										alt={fav.movie.originalTitle}
										src={fav.movie.posterPath ? `https://image.tmdb.org/t/p/w500${fav.movie.posterPath}` : 'https://placehold.co/300x450?text=No+Image'}
										style={{
											width: '100%',
											height: '350px',
											objectFit: 'cover',
											cursor: 'pointer'
										}}
										onClick={() => handleShowMovie(fav.movie)}
									/>}
									actions={[
										<Tooltip title="Remove from Favorites" key="remove"><Button type="text" danger icon={<DeleteOutlined />} onClick={() => handleRemove(fav.movie.id)} /></Tooltip>,
									]}
								>
									<Meta title={<Tooltip title={fav.movie.originalTitle}>{fav.movie.originalTitle}</Tooltip>} />
								</Card>
							</List.Item>
						)}
					/>
				) : (
					<Empty description="You have no favorite movies yet.">
						<Button type="primary"><Link href="/user/main">Discover Movies</Link></Button>
					</Empty>
				)
			)}
			<Modal
				open={modalIndex !== -1}
				onCancel={handleModalClose}
				footer={null}
				width={800}
				title={modalTitle}
				bodyStyle={{ minHeight: '450px' }}
			>
				{modalDataLoading ? <div style={{ textAlign: 'center', padding: '50px' }}><Spin /></div> :
					currentModalData?.type === 'movie' ? renderMovieModalContent(currentModalData.data) :
						currentModalData?.type === 'director' ? renderDirectorModalContent(currentModalData.data) : null
				}
			</Modal>
		</UserLayout>
	);
};

export default FavoritesPage;
