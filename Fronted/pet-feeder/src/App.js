
import Register from "./pages/Register/Register";
import Welcome from "./pages/Welcome/Welcome";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LogIn from "./pages/LogIn/LogIn";
import './App.css';

function App() {
  return (
    <Router> 
      <Routes>
      <Route path="/Welcome" element={<Welcome />} />
      <Route path="/Register" element={<Register />} />
      <Route path="/LogIn" element={<LogIn />} />
      </Routes>
    </Router>
  );
}

export default App;
