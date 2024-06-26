import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./Hotel.css";
import Tabs from "../Tabs/Tabs";
import CustomerService from "../Profile/CustomerService/CustomerService.jsx";
import AccountDetails from "../AccountDetails/AccountDetails.jsx";
import Password from "../Profile/Password/Password.jsx";
function Hotel() {
  const [cityNames, setCityNames] = useState([]);
  const [formData, setFormData] = useState({
    city: "",
    roomType: "",
    checkin: getDefaultDate(),
    checkout: getDefaultDate(),
  });
  const navigate = useNavigate();
  const [hotelRooms, setHotelRooms] = useState([]);
  const config = {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("accesstoken")}`,
    },
  };
  useEffect(() => {
    async function fetchCityNames() {
      try {
        const response = await axios.get(
          "http://localhost:8080/CS/Hotel/getallhotelcitynames",
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
    }
    setFormData({ ...formData, [name]: value });
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get(
        `http://localhost:8080/CS/Hotel/HotelBookingDetails/availablerooms/${formData.city}/${formData.roomType}/${formData.checkin}/${formData.checkout}`,
        config
      );
      setHotelRooms(response.data);
      console.log(response.data);
    } catch (error) {
      console.log(error.response.data.message);
    }
  };
  async function handleBookRoom(e) {
    const bookingDetails = {
      roomno: e.target.getAttribute("roomno"),
      hotelname: e.target.getAttribute("hotelname"),
      booked_from: formData.checkin,
      booked_to: formData.checkout,
    };
    try {
      const response = await axios.post(
        `http://localhost:8080/CS/Hotel/HotelBookingDetails/bookroom/${localStorage.getItem(
          `username`
        )}`,
        bookingDetails,
        config
      );
      navigate(`/hotelBookingDetails/${bookingDetails.hotelname}`);
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
      <div className="hoteldisplay">
        {hotelRooms.length > 0 ? (
          hotelRooms.map((hotel, index) => (
            <div key={index} className="hotel">
              <div className="hoteldetails">
                <div>
                  <h2>{hotel.hotelName}</h2>
                  <p>{hotel.address}</p>
                  <p>{hotel.description}</p>
                </div>
                <div>
                  <img id="imgh" src={hotel.hotelImage} />
                </div>
              </div>
              <h3>Rooms:</h3>
              {hotel.rooms.map((room) => (
                <div key={room.roomId} className="room">
                  <div>
                    <img id="imgr" src={room.roomImage} />
                  </div>
                  <div className="roomdisplay">
                    <div className="roomdetails">
                      <p>Room Number: {room.room_no}</p>
                      <p>Rate per Day: &#8377; {room.rate_per_day}</p>
                      <p>Room Type: {room.roomtype}</p>
                    </div>
                    <div id="roombutton">
                      <button
                        hotelname={hotel.hotelName}
                        roomno={room.room_no}
                        onClick={(e) => handleBookRoom(e)}
                      >
                        Book
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          ))
        ) : (
          <p>No hotels found for the selected city.</p>
        )}
      </div>
      <AccountDetails />
      <Password />
      <CustomerService />
    </>
  );
}

export default Hotel;
