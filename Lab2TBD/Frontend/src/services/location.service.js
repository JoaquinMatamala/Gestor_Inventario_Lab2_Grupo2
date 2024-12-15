import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class LocationService {
    // Crear una nueva ubicación
    async saveLocation(geoJson) {
      return axios.post(`${API_URL}`, geoJson); // POST /api/locations
    }
  
    // Obtener ubicación por ID
    async getLocationById(id) {
      return axios.get(`${API_URL}/getLocation/${id}`); // GET /api/locations/getLocation/{id}
    }
  
    // Obtener ubicación por tipo
    async getLocationByType(type) {
      return axios.get(`${API_URL}/getType`, {
        params: { type }, // GET /api/locations/getType?type=home
      });
    }
  }
  
  export default new LocationService();