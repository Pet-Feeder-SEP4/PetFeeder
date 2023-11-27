
import Register from "./pages/Register/Register";
import LogIn from './pages/LogIn/LogIn';
import Aboutpage from "./pages/AboutPage/AboutPage";
import MainPage from "./pages/MainPage/MainPage";
import Welcome from "./pages/Welcome/Welcome";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';


function App() {
  return (
    <Router> 
      <Routes>
      <Route path="/Welcome" element={<Welcome />} />
      <Route path="/Register" element={<Register />} />
      <Route path="/LogIn" element={<LogIn />} />
      <Route path="/AboutPage" element={<Aboutpage />} />
      <Route path="/MainPage" element={<MainPage />} />
      </Routes>
    </Router>
  );
}

export default App;
