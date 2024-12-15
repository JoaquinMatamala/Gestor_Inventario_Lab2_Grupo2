import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP + "/api/locations";

class LocationService {
  // Obtener el token del localStorage
  getAuthHeader() {
    const token = localStorage.getItem("jwtToken");
    return token ? { Authorization: `Bearer ${token}` } : {};
  }

  // Crear una nueva ubicación
  async saveLocation(geoJson) {
    console.log("📤 Enviando ubicación al backend:", geoJson);
    return axios.post(`${API_URL}`, geoJson, {
      headers: this.getAuthHeader(), // Añadir token al header
    });
  }

  // Obtener ubicación por ID
  async getLocationById(id) {
    console.log(`📥 Solicitando ubicación con ID: ${id}`);
    return axios.get(`${API_URL}/getLocation/${id}`, {
      headers: this.getAuthHeader(),
    });
  }

  // Obtener ubicación por tipo
  async getLocationByType(type) {
    console.log(`📥 Solicitando ubicaciones de tipo: ${type}`);
    return axios.get(`${API_URL}/getType`, {
      headers: this.getAuthHeader(),
      params: { type },
    });
  }
}

export default new LocationService();
