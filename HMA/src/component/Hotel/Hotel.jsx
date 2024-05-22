import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Hotel.css";
import { useNavigate } from "react-router-dom";
import Tabs from "./Tabs";
function Hotel() {
  const [cityNames, setCityNames] = useState([]);
  const [password, setPassword] = useState("");
  const [city, setCity] = useState("");
  const [formData, setFormData] = useState({
    city: "",
    roomType: "",
    checkin: getDefaultDate(),
    checkout: getDefaultDate(),
  });
  const [hotels, setHotels] = useState([]);
  const [hotelRooms, setHotelRooms] = useState([]);
  const navigate = useNavigate();
  const config = {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("accesstoken")}`,
    },
  };
  useEffect(() => {
    async function fetchCityNames() {
      try {
        const response = await axios.get(
          "http://localhost:8080/HMA/Hotel/getallhotelcitynames",
          config
        );
        setCityNames(response.data);
        console.log(response.data);
      } catch (error) {
        console.log(error.response.data.message);
      }
    }
    fetchCityNames();
  }, []);
  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "city") {
      setFormData({ ...formData, [name]: value });
      setCity(value);
    }
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get(
        `http://localhost:8080/HMA/BookingDetails/availablerooms/${formData.city}/${formData.roomType}/${formData.checkin}/${formData.checkout}`,
        config
      );
      setHotelRooms(response.data);
      console.log(response.data);
    } catch (error) {
      console.log(error.response.data.message);
    }
  };
  async function handlePasswordChange() {
    try {
      const response = await axios.put(
        `http://localhost:8080/HMA/User/updatepassword/${localStorage.getItem(
          `username`
        )}/${password}`,
        config
      );
      console.log(response.data);
    } catch (error) {
      console.log(error.response.data.message);
    }
  }
  function getDefaultDate() {
    const today = new Date();
    const day = String(today.getDate()).padStart(2, "0");
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const year = today.getFullYear();
    return `${year}-${month}-${day}`;
  }

  function changePassword() {
    const element = document.getElementById("passwordclass");
    if (element.style.display == "none") {
      element.style.display = "flex";
    } else {
      element.style.display = "none";
    }
  }

  function routeTo(nav) {
    navigate(nav);
  }
  function logout() {
    localStorage.removeItem(`accesstoken`);
    localStorage.removeItem(`refreshtoken`);
    localStorage.removeItem(`username`);
    navigate("/login");
  }
  return (
    <>
      <div id="hotelclass">
        <Tabs />
        <form className="formclass" onSubmit={handleSubmit}>
          <div className="divclass">
            <label htmlFor="city">City:</label>
            <select
              className="cityselect"
              id="city"
              name="city"
              value={formData.city}
              onChange={handleChange}
            >
              <option value="">Select city name</option>
              {cityNames.map((cityName, index) => (
                <option key={index} value={cityName}>
                  {cityName}
                </option>
              ))}
            </select>
          </div>

          <div className="divclass">
            <label htmlFor="checkin">Check-In:</label>
            <input
              type="date"
              name="checkin"
              id="checkin"
              value={formData.checkin}
              onChange={handleChange}
            />
          </div>
          <div className="divclass">
            <label htmlFor="checkout">Check-Out:</label>
            <input
              type="date"
              name="checkout"
              id="checkout"
              value={formData.checkout}
              onChange={handleChange}
            />
          </div>
          <div className="divclass">
            <label htmlFor="roomType">RoomType:</label>
            <select
              className="roomselect"
              id="roomType"
              name="roomType"
              value={formData.roomType}
              onChange={handleChange}
            >
              <option value="">Select Room Type</option>
              <option value="Single_Room">Single Room</option>
              <option value="Standard_Twin_Room">Standard Twin Room</option>
              <option value="Standard_Double_Room">Standard Double Room</option>
              <option value="Deluxe_Double_Room">Deluxe Double Room</option>
              <option value="Triple_room">Triple room</option>
              <option value="Quad_room">Quad room</option>
              <option value="Suite">Suite</option>
            </select>
          </div>
          <div>
            <button type="submit">Search</button>
          </div>
        </form>
      </div>
      <div>
        {hotels.length > 0 ? (
          <ul>
            {hotels.map((hotel, index) => (
              <li key={index}>{hotel}</li>
            ))}
          </ul>
        ) : (
          <p>No hotels found for the selected city.</p>
        )}
      </div>
      <div id="profile">
        <div>
          <h3>Account Details</h3>
        </div>
        <div id="ptab">
          <button onClick={(n) => routeTo("/profile")}>
            <img id="img" src="./profile.png" />
            Profile
          </button>
        </div>
        <div id="ptab">
          <button onClick={changePassword}>
            <img id="img" src="./password.png" />
            Change Password
          </button>
        </div>
        <div id="ptab">
          <button onClick={(n) => routeTo("/saveTraveller")}>
            <img id="img" src="./traveller.png" />
            Save Travellers
          </button>
        </div>
        <div id="ptab">
          <button onClick={logout}>
            <img id="img" src="./logout.png" />
            Logout
          </button>
        </div>
      </div>
      <div id="passwordclass">
        <div className="ptab">
          <h3>Change Password</h3>
        </div>
        <div className="ptab">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            name="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <div className="ptab" id="submit">
          <button id="c" onClick={changePassword}>
            Cancel
          </button>
          <button id="s" onClick={handlePasswordChange}>
            Save
          </button>
        </div>
      </div>
      <div id="csclass">
        <h3>Customer Care</h3>
        <p>Mobile: 9876543210</p>
        <p>Email: customercare@gmail.com</p>
      </div>
    </>
  );
}

export default Hotel;
