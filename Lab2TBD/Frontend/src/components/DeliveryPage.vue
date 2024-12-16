<template>
    <div class="data-container">
      <h2 class="text-center mb-4">Órdenes Disponibles</h2>
      <table class="table table-striped">
        <thead>
          <tr>
            <th>ID</th>
            <th>Total</th>
            <th>Fecha</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.id">
            <td>{{ order.id }}</td>
            <td>{{ order.total }}</td>
            <td>{{ new Date(order.date).toLocaleDateString() }}</td>
            <td>
              <button class="btn btn-info btn-sm" @click="viewOrderDetails(order.id)">
                Ver Detalles
              </button>
            </td>
          </tr>
        </tbody>
      </table>
  
      <div v-if="selectedOrderDetails" class="order-details mt-4">
        <h3>Detalles de la Orden #{{ selectedOrderDetails[0]?.order_id }}</h3>
        <table class="table table-bordered">
          <thead>
            <tr>
              <th>Producto</th>
              <th>Cantidad</th>
              <th>Precio</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="detail in selectedOrderDetails" :key="detail.product_id">
              <td>{{ detail.product_name }}</td>
              <td>{{ detail.quantity }}</td>
              <td>${{ detail.price.toFixed(2) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
  
      <button class="btn btn-primary w-100 mt-3" @click="fetchOrders">Actualizar Órdenes</button>
    </div>
  </template>
  
  <script>
  import OrderService from "@/services/order.service";
  
  export default {
    name: "ViewOrders",
    data() {
      return {
        orders: [], // Lista de órdenes
        selectedOrderDetails: null, // Detalles de la orden seleccionada
      };
    },
    methods: {
      // Obtener todas las órdenes
      async fetchOrders() {
        try {
          const orders = await OrderService.getAllOrders(); // Usar el método del servicio
          this.orders = orders.map((order) => ({
            ...order,
            total: order.total || 0, // Asegurar que siempre haya un total
          }));
        } catch (error) {
          console.error(
            "Error al obtener las órdenes:",
            error.response?.data || error.message
          );
          alert("No se pudieron cargar las órdenes. Intenta nuevamente.");
        }
      },
      // Obtener detalles de una orden específica
      async viewOrderDetails(orderId) {
        try {
          const details = await OrderService.getOrderDetailsByOrderId(orderId);
  
          // Agregar nombres de productos a los detalles
          const detailsWithProductNames = await Promise.all(
            details.map(async (detail) => {
              try {
                const product = await OrderService.getProductById(detail.product_id);
                return {
                  ...detail,
                  product_name: product.product_name,
                };
              } catch (error) {
                console.error(
                  `Error al obtener el producto con ID ${detail.product_id}:`,
                  error.message
                );
                return { ...detail, product_name: "Nombre no disponible" };
              }
            })
          );
  
          this.selectedOrderDetails = detailsWithProductNames;
        } catch (error) {
          console.error(
            "Error al obtener los detalles de la orden:",
            error.response?.data || error.message
          );
          alert("No se pudieron cargar los detalles de la orden.");
        }
      },
    },
    mounted() {
      this.fetchOrders(); // Cargar órdenes al montar el componente
    },
  };
  </script>
  
  <style scoped>
  .data-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f8f9fa;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  h2 {
    color: #333;
    margin-bottom: 20px;
  }
  
  .table th,
  .table td {
    text-align: center;
    vertical-align: middle;
  }
  
  .order-details {
    padding: 20px;
    background-color: #ffffff;
    border: 1px solid #ddd;
    border-radius: 10px;
  }
  </style>
  