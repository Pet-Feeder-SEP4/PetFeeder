
import Register from "./pages/Register/Register";
import Welcome from "./pages/Welcome/Welcome";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import FeedingSchedule from "./pages/Schedule/Schedule";
import LogIn from "./pages/LogIn/LogIn";
import CreatePet from "./pages/CreatePet/CreatePet";
import MainPage from "./pages/MainPage/MainPage";
import EditPet from "./pages/EditPet/EditPet";

function App() {
  return (
    <Router> 
      <Routes>
      <Route path="/Welcome" element={<Welcome />} />
      <Route path="/Register" element={<Register />} />
      <Route path="/Login" element={<LogIn />} />
      <Route path="/schedule/:petFeederId" element={<FeedingSchedule />} /> 
      <Route path="/CreatePet" element={<CreatePet />} />
      <Route path="/MainPage" element={<MainPage />} />
      <Route path="/EditPet/:petId" element={<EditPet />} />
      
      </Routes>
    </Router>
  );
}

export default App;
