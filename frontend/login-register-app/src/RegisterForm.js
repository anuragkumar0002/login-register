import React, { useState } from 'react';
import axios from 'axios';
import { TextField, Button, Container, Typography, Box, CssBaseline, Alert } from '@mui/material';

function RegisterForm() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/register', { username, password });
            if (response.data === 'Success') {
                setMessage('Registration successful! Please login.');
            } else {
                setMessage('Registration failed: Username already exists');
            }
        } catch (error) {
            setMessage('Error registering');
        }
    };

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Typography component="h1" variant="h5">
                    Register
                </Typography>
                <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1 }}>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        label="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        label="Password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Register
                    </Button>
                    {message && (
                        <Alert severity={message.includes('successful') ? 'success' : 'error'} sx={{ mt: 2 }}>
                            {message}
                        </Alert>
                    )}
                </Box>
            </Box>
        </Container>
    );
}

export default RegisterForm;
