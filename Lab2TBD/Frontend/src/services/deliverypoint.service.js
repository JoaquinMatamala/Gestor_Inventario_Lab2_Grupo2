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

  // Obtener location_id desde deliveryPointId
  async getLocationIdByDeliveryPointId(deliveryPointId) {
    try {
      console.log(`📥 Obteniendo location_id para deliveryPointId: ${deliveryPointId}`);
      const response = await axios.get(
        `${API_DELIVERY_POINT_URL}/get-location-id/${deliveryPointId}`,
        { headers: this.getAuthHeader() }
      );
      console.log("✅ location_id obtenido:", response.data);
      return response.data;
    } catch (error) {
      console.error("❌ Error al obtener location_id:", error.response?.data || error.message);
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

  // Actualizar deliveryManId en un deliveryPoint
  async updateDeliveryManId(deliveryPointId, deliveryManId) {
    try {
        console.log(`📥 Actualizando DeliveryManId: ${deliveryManId} para DeliveryPointId: ${deliveryPointId}`);
        
        await axios.post(
            `${API_DELIVERY_POINT_URL}/update-deliveryman`,
            null, // Sin cuerpo porque los parámetros están en la query string
            {
                headers: this.getAuthHeader(), // Incluye el token en el encabezado
                params: {
                    deliveryPointId,
                    deliveryManId,
                },
            }
        );

        console.log("✅ DeliveryMan actualizado correctamente.");
    } catch (error) {
        console.error("❌ Error al actualizar el DeliveryMan:", error.response?.data || error.message);
        throw error;
    }
}

    // Método para agregar una reseña a un DeliveryPoint
    async reviewDeliveryPoint(deliveryPointId, reviewData) {
      try {
        console.log(`📥 Enviando reseña para DeliveryPointId: ${deliveryPointId}`, reviewData);
  
        const response = await axios.post(
          `${API_DELIVERY_POINT_URL}/${deliveryPointId}/review`,
          {
            rating: reviewData.rating,   // Puntuación de 0 a 5
            comment: reviewData.comment, // Comentario descriptivo
          },
          {
            headers: this.getAuthHeader(),
          }
        );
  
        console.log("✅ Reseña enviada correctamente:", response.data);
        return response.data; // Devuelve la respuesta del backend
      } catch (error) {
        console.error("❌ Error al enviar la reseña:", error.response?.data || error.message);
        throw error;
      }
    }

    async getDeliveryPointsByDeliveryManId(deliveryManId) {
      try {
        console.log(`📥 Obteniendo DeliveryPoints para deliveryManId: ${deliveryManId}`);
        const response = await axios.get(
          `${API_DELIVERY_POINT_URL}/search/deliveryman/${deliveryManId}`,
          { headers: this.getAuthHeader() }
        );
        console.log("✅ DeliveryPoints obtenidos:", response.data);
        return response.data; // Devuelve la lista de DeliveryPoints
      } catch (error) {
        console.error(
          "❌ Error al obtener los DeliveryPoints:",
          error.response?.data || error.message
        );
        throw error;
      }
    }
  

}

export default new DeliveryPointService();
