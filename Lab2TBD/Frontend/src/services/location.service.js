import axios from "axios";

const API_LOCATION_URL = process.env.VUE_APP_BACKEND_IP + "/api/locations";
const API_CLIENT_URL = process.env.VUE_APP_BACKEND_IP + "/client";

class LocationService {
  getAuthHeader() {
    const token = localStorage.getItem("jwtToken");
    return token ? { Authorization: `Bearer ${token}` } : {};
  }

  // Crear una nueva ubicación y devolver su ID
  async saveLocation(geoJson) {
    console.log("📤 Enviando ubicación al backend:", geoJson);
    const response = await axios.post(`${API_LOCATION_URL}`, geoJson, {
      headers: this.getAuthHeader(),
    });
    return response.data.locationId;
  }

  // Asignar ubicación como home_location
  async assignHomeLocation(clientId, locationId) {
    console.log(`📤 Asignando ubicación ${locationId} al cliente ${clientId}`);
    return axios.post(`${API_CLIENT_URL}/assign-home-location`, null, {
      headers: this.getAuthHeader(),
      params: { clientId, locationId },
    });
  }

  // Obtener la ubicación con el ID más grande
  async getLocationWithMaxId() {
    console.log("📥 Solicitando ubicación con el ID más grande");
    const response = await axios.get(`${API_LOCATION_URL}/max-id`, {
      headers: this.getAuthHeader(),
    });
    return response.data;
  }

  // Obtener una ubicación por ID
  async getLocationById(locationId) {
    console.log(`📥 Solicitando ubicación con ID: ${locationId}`);
    const response = await axios.get(`${API_LOCATION_URL}/getLocation/${locationId}`, {
      
      headers: this.getAuthHeader(),
    });
    return response.data;
  }


}

export default new LocationService();
