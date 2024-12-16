<template>
  <div class="data-container">
    <h2 class="text-center mb-4">Órdenes Disponibles</h2>
    <table class="table table-striped">
      <thead>
        <tr>
          <th>ID Orden</th>
          <th>Nombre Cliente</th>
          <th>Estado</th>
          <th>Dirección</th>
          <th>Fecha</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="order in orders" :key="order.order_id">
          <td>{{ order.order_id }}</td>
          <td>{{ order.client_name }}</td> <!-- Mostrar nombre del cliente -->
          <td>{{ order.status }}</td>
          <td>
            {{ order.address }}
            <button
              class="btn btn-primary btn-sm ms-2"
              @click="viewOnMap(order.delivery_point_id)"
            >
              Ver en mapa
            </button>
          </td>
          <td>{{ new Date(order.date).toLocaleDateString() }}</td>
          <td>
            <button class="btn btn-info btn-sm">
              Aceptar
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
import ClientService from "@/services/client.service";
import DeliveryPointService from "@/services/deliverypoint.service";

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
        const orders = await OrderService.getAllOrders();

        // Obtener el nombre del cliente para cada orden
        const ordersWithClientNames = await Promise.all(
          orders.map(async (order) => {
            const clientName = await ClientService.getClientName(order.client_id);
            return { ...order, client_name: clientName };
          })
        );

        this.orders = ordersWithClientNames;
      } catch (error) {
        console.error("Error al obtener las órdenes:", error.response?.data || error.message);
        alert("No se pudieron cargar las órdenes. Intenta nuevamente.");
      }
    },

    // Redirigir al mapa con location_id obtenido por delivery_point_id
    async viewOnMap(deliveryPointId) {
      try {
        const locationId = await DeliveryPointService.getLocationIdByDeliveryPointId(deliveryPointId);
        if (!locationId) {
          alert("No se encontró una ubicación para esta orden.");
          return;
        }
        this.$router.push(`/location-viewer?locationId=${locationId}`);
      } catch (error) {
        console.error("Error al redirigir al mapa:", error.response?.data || error.message);
        alert("No se pudo redirigir al mapa. Intenta nuevamente.");
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

.btn-primary {
  background-color: #007bff;
  border: none;
  transition: background-color 0.3s ease;
}

.btn-primary:hover {
  background-color: #0056b3;
}
</style>
