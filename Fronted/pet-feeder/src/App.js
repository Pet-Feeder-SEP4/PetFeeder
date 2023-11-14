
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import Welcome from './Pages/Welcome/Welcome';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {
  return (
    <Router>
      
        <Routes>
          
          <Route path="/Welcome" element={<Welcome />} />
          
        </Routes>
      
    </Router>
    
  );
}

export default App;
