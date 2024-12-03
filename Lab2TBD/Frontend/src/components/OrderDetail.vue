<template>
    <div class="order-detail-container">
      <h3>Detalles de la Orden #{{ orderId }}</h3>
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Precio Unitario</th>
            <th>Total</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="detail in details" :key="detail.id">
            <td>{{ detail.product_name }}</td>
            <td>{{ detail.quantity }}</td>
            <td>{{ formatCurrency(detail.price) }}</td>
            <td>{{ formatCurrency(detail.quantity * detail.price) }}</td>
          </tr>
        </tbody>
      </table>
      <button class="btn btn-secondary mt-3" @click="$router.push('/orders')">
        Volver a Órdenes
      </button>
    </div>
  </template>
  
  <script>
  import OrderService from "@/services/order.service";
  
  export default {
    name: "OrderDetail",
    props: ["orderId"], // Recibe orderId como prop
    data() {
      return {
        details: [], // Detalles de la orden
      };
    },
    methods: {
      async fetchOrderDetails() {
        try {
          const details = await OrderService.getOrderDetailsByOrderId(this.orderId);
          this.details = details;
        } catch (error) {
          console.error("Error al obtener los detalles de la orden:", error.response?.data || error.message);
          alert("Hubo un error al cargar los detalles de la orden.");
        }
      },
      formatCurrency(value) {
        return `$${value.toFixed(2)}`;
      },
    },
    mounted() {
      this.fetchOrderDetails();
    },
  };
  </script>
  
  <style scoped>
  .order-detail-container {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    background-color: #ffffff;
    border: 1px solid #ddd;
    border-radius: 8px;
  }
  </style>
  