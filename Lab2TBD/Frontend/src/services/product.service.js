import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class ProductService {
  // Método para obtener todos los productos
  async getAllProducts() {
    try {
      const response = await axios.get(`${API_URL}/product/getall`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error al obtener productos:", error.response?.data || error.message);
      throw error;
    }
  }

  // Método para buscar producto por ID
  async getProductById(productId) {
    try {
      const response = await axios.get(`${API_URL}/product/search/id/${productId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error al buscar producto por ID:", error.response?.data || error.message);
      throw error;
    }
  }

  // Métodos adicionales según las necesidades del backend
}

export default new ProductService();
