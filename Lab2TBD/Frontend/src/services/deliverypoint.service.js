import axios from "axios";

const API_DELIVERY_POINT_URL = process.env.VUE_APP_BACKEND_IP + "/deliveryPoint";

class DeliveryPointService {
  getAuthHeader() {
    const token = localStorage.getItem("jwtToken");
    return token ? { Authorization: `Bearer ${token}` } : {};
  }

  // Obtener todos los DeliveryPoints asociados a un cliente
  async getAllDeliveryPointsByClient(clientId) {
    try {
      console.log(`📥 Obteniendo todos los DeliveryPoints para el cliente con ID: ${clientId}`);
      const response = await axios.get(`${API_DELIVERY_POINT_URL}/getallbyclient/${clientId}`, {
        headers: this.getAuthHeader(),
      });
      return response.data; // Retorna la lista de DeliveryPoints
    } catch (error) {
      console.error("Error al obtener los DeliveryPoints del cliente:", error.response?.data || error.message);
      throw error;
    }
  }

  // Buscar un DeliveryPoint por ID
  async getDeliveryPointById(deliveryPointId) {
    try {
      console.log(`📥 Buscando DeliveryPoint por ID: ${deliveryPointId}`);
      const response = await axios.get(`${API_DELIVERY_POINT_URL}/search/id/${deliveryPointId}`, {
        headers: this.getAuthHeader(),
      });
      return response.data; // Retorna el DeliveryPoint
    } catch (error) {
      console.error("Error al buscar el DeliveryPoint por ID:", error.response?.data || error.message);
      throw error;
    }
  }

  // Buscar un DeliveryPoint por nombre
  async getDeliveryPointByName(name) {
    try {
      console.log(`📥 Buscando DeliveryPoint por nombre: ${name}`);
      const response = await axios.get(`${API_DELIVERY_POINT_URL}/search/name`, {
        headers: this.getAuthHeader(),
        params: { name },
      });
      return response.data; // Retorna el DeliveryPoint
    } catch (error) {
      console.error("Error al buscar el DeliveryPoint por nombre:", error.response?.data || error.message);
      throw error;
    }
  }

  // Crear un nuevo DeliveryPoint
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
      return response.data; // Retorna el mensaje de éxito
    } catch (error) {
      console.error("Error al crear el DeliveryPoint:", error.response?.data || error.message);
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
      return response.data; // Retorna el DeliveryPoint encontrado
    } catch (error) {
      if (error.response?.status === 404) {
        console.log("No se encontró un DeliveryPoint existente.");
        return null; // Retorna null si no se encuentra
      }
      console.error("Error al buscar el DeliveryPoint existente:", error.response?.data || error.message);
      throw error;
    }
  }
}

export default new DeliveryPointService();
