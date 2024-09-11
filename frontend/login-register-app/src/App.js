import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginForm from './LoginForm';
import RegisterForm from './RegisterForm';
import WelcomePage from './WelcomePage';

function App() {
    return (
        <Router>
            <div>
                <Routes>
                    <Route path="/login" element={<LoginForm />} />
                    <Route path="/register" element={<RegisterForm />} />
                    <Route path="/welcome" element={<WelcomePage />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
