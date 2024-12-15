import axios from "axios";

const API_DELIVERY_POINT_URL = process.env.VUE_APP_BACKEND_IP + "/deliveryPoint";

class DeliveryPointService {
  getAuthHeader() {
    const token = localStorage.getItem("jwtToken");
    return token ? { Authorization: `Bearer ${token}` } : {};
  }

  async createDeliveryPoint(name, status, comment, locationPoint, clientId) {
    try {
      console.log("📤 Creando nuevo DeliveryPoint:", {
        name,
        status,
        comment,
        locationPoint,
        clientId,
      });
  
      const response = await axios.post(
        `${API_DELIVERY_POINT_URL}/create`,
        {
          delivery_point_name: name,
          status_point: status,
          comment: comment,
          delivery_location_point: locationPoint,
          client_id: clientId,
        },
        {
          headers: this.getAuthHeader(),
        }
      );
  
      // Convertir directamente a entero
      const deliveryPointId = parseInt(response.data, 10);
  
      console.log("✅ DeliveryPoint creado exitosamente con ID:", deliveryPointId);
      return deliveryPointId; // Retorna el ID
    } catch (error) {
      console.error("❌ Error al crear el DeliveryPoint:", error.response?.data || error.message);
      throw error;
    }
  }
  
  
  
  
  
  

  // Buscar un DeliveryPoint existente para un cliente y ubicación
  async findExistingDeliveryPoint(clientId, locationId) {
    try {
      console.log(`📥 Buscando DeliveryPoint existente para cliente: ${clientId}, ubicación: ${locationId}`);
      const response = await axios.get(`${API_DELIVERY_POINT_URL}/search-existing`, {
        headers: this.getAuthHeader(),
        params: { clientId, locationId },
      });
      console.log("✅ DeliveryPoint encontrado:", response.data);
      return response.data; // Retorna el DeliveryPoint encontrado
    } catch (error) {
      if (error.response?.status === 404) {
        console.log("🔍 No se encontró un DeliveryPoint existente para la combinación cliente-ubicación.");
        return null; // Retorna null si no se encuentra
      }
      console.error("❌ Error al buscar el DeliveryPoint existente:", error.response?.data || error.message);
      throw error;
    }
  }
}

export default new DeliveryPointService();
