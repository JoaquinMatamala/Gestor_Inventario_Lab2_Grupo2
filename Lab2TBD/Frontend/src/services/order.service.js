import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class OrderService {
  async createOrder(orderData) {
    try {
      console.log("Datos de la orden enviados al backend:", JSON.stringify(orderData, null, 2));

      const response = await axios.post(`${API_URL}/order/new-order`, orderData, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });

      console.log("Respuesta del backend al crear la orden:", response.data);
      const orderId = response.data;
      return orderId;
    } catch (error) {
      console.error("Error al crear la orden:", error.response?.data || error.message);
      throw error;
    }
  }
  async createOrderDetail(orderDetail) {
    try {
      console.log("Datos del detalle de la orden enviados al backend:", JSON.stringify(orderDetail, null, 2));

      const response = await axios.post(`${API_URL}/order-detail/create`, orderDetail, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });

      console.log("Respuesta del backend al crear el detalle de la orden:", response.data);
    } catch (error) {
      console.error("Error al crear el detalle de la orden:", error.response?.data || error.message);
      throw error;
    }
  }
  async getOrdersByClientId(clientId) {
    try {
      const response = await axios.get(`${API_URL}/order/search/clientId/${clientId}`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error al obtener las órdenes:", error.response?.data || error.message);
      throw error;
    }
  }
  async deleteOrderById(orderId) {
    try {
        
      // Llamada al backend para eliminar la orden
      const response = await axios.delete(`${API_URL}/order/delete/${orderId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`, // Token si es necesario
        },
      });
  
      if (response.status === 204) {
        alert("Orden eliminada exitosamente.");
      }
    } catch (error) {
      console.error("Error al eliminar la orden:", error.response?.data || error.message);
      alert("Hubo un error al intentar eliminar la orden. Intenta nuevamente más tarde.");
    }
  }
  

  async getOrderDetailsByOrderId(orderId) {
    try {
      const response = await axios.get(`${API_URL}/order-detail/filter/order/${orderId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error al obtener los detalles de la orden:", error.response?.data || error.message);
      throw error;
    }
  }

  async payOrder(orderId) {
    try {
      const response = await axios.put(`${API_URL}/order/update-stock/${orderId}`, null, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      console.log("Respuesta del backend al pagar la orden:", response.data);
    } catch (error) {
      console.error("Error al pagar la orden:", error.response?.data || error.message);
      throw error;
    }
  }
  async getProductById(productId) {
    try {
      const response = await axios.get(`${API_URL}/product/search/id/${productId}`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error(`Error al obtener el producto con ID ${productId}:`, error.response?.data || error.message);
      throw error;
    }
  }
  async updateOrderStatus(orderId, status) {
    try {
      const response = await axios.put(
        `${API_URL}/order/update-status/${orderId}`,
        { status },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
        }
      );
      console.log("Respuesta del backend al actualizar el estado:", response.data);
      return response.data;
    } catch (error) {
      console.error("Error al actualizar el estado de la orden:", error.response?.data || error.message);
      throw error;
    }
  }
  
  
  
  
}


export default new OrderService();
