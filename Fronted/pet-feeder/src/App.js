
import Register from "./pages/Register/Register";
import Welcome from "./pages/Welcome/Welcome";
import LogIn from "./pages/LogIn/LogIn"
import MainPage from "./pages/MainPage/MainPage";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import FeedingSchedule from "./pages/Schedule/Schedule";
import CreatePet from "./pages/CreatePet/CreatePet";

function App() {
  return (
    <Router> 
      <Routes>
      <Route path="/MainPage" element={<MainPage />} />
      <Route path="/Welcome" element={<Welcome />} />
      <Route path="/Register" element={<Register />} />
      <Route path="/Login" element={<LogIn />} />
      <Route path="/Schedule" element={<FeedingSchedule />} />
      <Route path="/CreatePet" element={<CreatePet />} />
      </Routes>
    </Router>
  );
}

export default App;
