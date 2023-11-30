
import Register from "./pages/Register/Register";
import Welcome from "./pages/Welcome/Welcome";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import FeedingSchedule from "./pages/Schedule/Schedule";
import LogIn from "./pages/LogIn/LogIn";

function App() {
  return (
    <Router> 
      <Routes>
      <Route path="/Welcome" element={<Welcome />} />
      <Route path="/Register" element={<Register />} />
      <Route path="/Login" element={<LogIn />} />
      <Route path="/Schedule" element={<FeedingSchedule />} />
      </Routes>
    </Router>
  );
}

export default App;
