import axios from "axios";

const API_ESTABLISHMENT_URL = process.env.VUE_APP_BACKEND_IP + "/establishment";

class EstablishmentService {
  // Obtener encabezado de autenticación si es necesario
  getAuthHeader() {
    const token = localStorage.getItem("jwtToken");
    return token ? { Authorization: `Bearer ${token}` } : {};
  }

  // Crear un nuevo establecimiento
  async buildEstablishment(establishmentData) {
    try {
      console.log("📤 Enviando datos del establecimiento al backend:", establishmentData);

      const response = await axios.post(
        `${API_ESTABLISHMENT_URL}/build`,
        establishmentData,
        { headers: this.getAuthHeader() }
      );

      console.log("✅ Establecimiento creado correctamenssssssssssste:", response.data);
      return response.data;
    } catch (error) {
      console.error("❌ Error al crear el establecimiento:", error.response?.data || error.message);
      throw error;
    }
  }

  async getAllEstablishments() {
    try {
      const response = await axios.get(`${API_ESTABLISHMENT_URL}/getall`, {
        headers: this.getAuthHeader(),
      });
      return response.data; // Retorna la lista de establecimientos
    } catch (error) {
      console.error("Error al obtener los establecimientos:", error.message);
      throw new Error("Error al cargar establecimientos.");
    }
  }
  async getAddressByLocationId(locationId) {
    try {
      const response = await axios.get(`${API_ESTABLISHMENT_URL}/getdetails/${locationId}`, {
        headers: this.getAuthHeader(),
      });
      console.log("✅ Dirección obtenida:", response.data);
      return response.data;
    } catch (error) {
      console.error("❌ Error al obtener la dirección:", error.response?.data || error.message);
      throw error;
    }
  }
  
  
}

export default new EstablishmentService();
