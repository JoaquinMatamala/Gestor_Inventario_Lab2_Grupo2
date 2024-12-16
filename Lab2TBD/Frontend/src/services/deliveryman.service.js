import axios from "axios";

const API_DELIVERYMAN_URL = process.env.VUE_APP_BACKEND_IP + "/deliveryman";

class DeliveryManService {
  getAuthHeader() {
    const token = localStorage.getItem("jwtToken");
    return token ? { Authorization: `Bearer ${token}` } : {};
  }

  // Crear un nuevo DeliveryMan
  async createDeliveryMan(clientId, establishmentId) {
    try {
      console.log("📤 Creando nuevo DeliveryMan:", { clientId, establishmentId });

      const response = await axios.post(
        `${API_DELIVERYMAN_URL}/create`,
        null, // No hay cuerpo explícito
        {
          params: {
            clientId,
            establishmentId,
          },
          headers: this.getAuthHeader(),
        }
      );

      console.log("✅ DeliveryMan creado exitosamente");
      return response.data;
    } catch (error) {
      console.error("❌ Error al crear el DeliveryMan:", error.response?.data || error.message);
      throw error;
    }
  }

  // Obtener un DeliveryMan por clientId
  async getDeliveryManByclientId(clientId) {
    try {
      console.log(`📥 Solicitando DeliveryMan para clientId: ${clientId}`);
      const response = await axios.get(`${API_DELIVERYMAN_URL}/get/${clientId}`, {
        headers: this.getAuthHeader(),
      });
      console.log("✅ DeliveryMan obtenido:", response.data);
      return response.data; // Retorna la entidad o null
    } catch (error) {
      console.error("❌ Error al obtener el DeliveryMan:", error.response?.data || error.message);
      throw error;
    }
  }
}

export default new DeliveryManService();
