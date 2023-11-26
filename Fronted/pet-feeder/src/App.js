
import Register from "./pages/Register/Register";
import Welcome from "./pages/Welcome/Welcome";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import CreatePet from "./pages/CreatePet/CreatePet";

function App() {
  return (
    <Router> 
      <Routes>
      <Route path="/Welcome" element={<Welcome />} />
      <Route path="/Register" element={<Register />} />
      <Route path="/CreatePet" element={<CreatePet />} />
      </Routes>
    </Router>
  );
}

export default App;
