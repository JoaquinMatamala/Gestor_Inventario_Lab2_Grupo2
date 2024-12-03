import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class AuditService {
  async getUserActionRanking() {
    try {
      console.log("Obteniendo el ranking de acciones de usuarios...");
      const response = await axios.get(`${API_URL}/api/audit/ranking`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      console.log("Ranking obtenido exitosamente:", response.data);
      return response.data;
    } catch (error) {
      console.error("Error al obtener el ranking de acciones de usuarios:", error.response?.data || error.message);
      throw error;
    }
  }

  async getUserLogs(userId) {
    try {
      console.log(`Obteniendo logs del usuario con ID ${userId}...`);
      const response = await axios.get(`${API_URL}/api/audit/logs/${userId}`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      console.log("Logs obtenidos exitosamente:", response.data);
      return response.data;
    } catch (error) {
      console.error(`Error al obtener los logs del usuario ${userId}:`, error.response?.data || error.message);
      throw error;
    }
  }
}

export default new AuditService();
