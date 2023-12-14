import Register from "./pages/Register/Register";
import Welcome from "./pages/Welcome/Welcome";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import FeedingSchedule from "./pages/Schedule/Schedule";
import LogIn from "./pages/LogIn/LogIn";
import CreatePet from "./pages/CreatePet/CreatePet";
import MainPage from "./pages/MainPage/MainPage";
import PetFeedersC from "./components/PetFeedersC/PetFeedersC";
import Dashboard from "./pages/Dashboard/Dashboard";
import Time from "./pages/Schedule/Time/Time";
import EditPet from "./pages/EditPet/EditPet";
import PetInfo from "./pages/PetInfo/PetInfo";
import PetFeederHistoryPage from "./pages/History/History";
import MyPet from "./pages/MyPet/MyPet";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Welcome />} />
        <Route path="/Register" element={<Register />} />
        <Route path="/Login" element={<LogIn />} />
        <Route path="/schedule/:petFeederId" element={<FeedingSchedule />} />
        <Route path="/CreatePet" element={<CreatePet />} />
        <Route path="/MainPage" element={<MainPage />} />
        <Route path="/EditPet/:petId" element={<EditPet />} />
        <Route path="/PetFeedersC" element={<PetFeedersC />} />
        <Route path="/dashboard/:petFeederId" element={<Dashboard />} />
        <Route path="/add-time/:scheduleId/:scheduleLabel" element={<Time />} />
        <Route path="/PetInfo/:petId" element={<PetInfo />} />
        <Route
          path="/History/:petFeederId"
          element={<PetFeederHistoryPage />}
        />
        <Route path="/mypet/:petFeederId" element={<MyPet />} />
      </Routes>
    </Router>
  );
}

export default App;
