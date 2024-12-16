<template>
  <div class="data-container">
    <h2 class="text-center mb-4">Órdenes Disponibles</h2>

    <!-- Tabla de "Por entregar" -->
    <div v-if="deliveryPoints.length > 0" class="mb-5">
      <h3 class="text-center">Órdenes Aceptadas</h3>
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
        <tr
          v-for="order in filteredOrders"
          :key="order.order_id"
        >
          <td>{{ order.order_id }}</td>
          <td>{{ order.client_name }}</td>
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
            <button
              class="btn btn-info btn-sm"
              @click="acceptOrder(order.delivery_point_id)"
            >
              Aceptar
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    </div>

    <!-- Tabla de órdenes disponibles -->
    <h3 class="text-center">Órdenes Disponibles</h3>
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
        <tr
          v-for="order in filteredOrders"
          :key="order.order_id"
        >
          <td>{{ order.order_id }}</td>
          <td>{{ order.client_name }}</td>
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
            <button
              class="btn btn-info btn-sm"
              @click="acceptOrder(order.delivery_point_id)"
            >
              Aceptar
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <button class="btn btn-primary w-100 mt-3" @click="fetchOrders">
      Actualizar Órdenes
    </button>
  </div>
</template>

<script>
import OrderService from "@/services/order.service";
import DeliveryManService from "@/services/deliveryman.service";
import DeliveryPointService from "@/services/deliverypoint.service";
import ClientService from "@/services/client.service";

export default {
  name: "ViewOrders",
  data() {
    return {
      orders: [], // Lista de todas las órdenes
      deliveryPoints: [], // Lista de órdenes aceptadas
    };
  },
  computed: {
    // Filtrar órdenes para mostrar solo las que tienen estado "Enviada"
    filteredOrders() {
      return this.orders.filter((order) => order.status === "Enviada");
    },
  },
  methods: {
    // Obtener todas las órdenes
    async fetchOrders() {
      try {
        const orders = await OrderService.getAllOrders();
        const ordersWithClientNames = await Promise.all(
          orders.map(async (order) => {
            const clientName = await ClientService.getClientName(order.client_id);
            return { ...order, client_name: clientName };
          })
        );
        this.orders = ordersWithClientNames;
        this.fetchDeliveryPoints(); // Cargar órdenes aceptadas
      } catch (error) {
        console.error("Error al obtener las órdenes:", error.response?.data || error.message);
        alert("No se pudieron cargar las órdenes. Intenta nuevamente.");
      }
    },

    // Obtener las órdenes aceptadas por el repartidor
    async fetchDeliveryPoints() {
      try {
        const clientId = localStorage.getItem("clientId");
        if (!clientId) return alert("Usuario no autenticado.");

        // Obtener DeliveryMan asociado al clientId
        const deliveryMan = await DeliveryManService.getDeliveryManByclientId(clientId);
        if (!deliveryMan) return alert("No se encontró un repartidor asociado.");

        // Obtener DeliveryPoints asociados al DeliveryMan
        const points = await DeliveryPointService.getDeliveryPointsByDeliveryManId(
          deliveryMan.deliveryman_id
        );

        // Filtrar puntos que tengan rating == null y enriquecer datos
        this.deliveryPoints = await Promise.all(
          points
            .filter((point) => point.rating === null)
            .map(async (point) => {
              const clientName = await ClientService.getClientName(point.client_id);
              return { ...point, client_name: clientName, status: "Aceptada" };
            })
        );
      } catch (error) {
        console.error(
          "Error al obtener los puntos de entrega:",
          error.response?.data || error.message
        );
      }
    },

    // Redirigir al mapa con location_id obtenido por delivery_point_id
    async viewOnMap(locationId) {
      try {
        this.$router.push(`/location-viewer?locationId=${locationId}`);
      } catch (error) {
        console.error("Error al redirigir al mapa:", error.response?.data || error.message);
        alert("No se pudo redirigir al mapa. Intenta nuevamente.");
      }
    },

    // Aceptar la orden
    async acceptOrder(deliveryPointId) {
      try {
        const clientId = localStorage.getItem("clientId");
        if (!clientId) return alert("Usuario no autenticado.");

        const deliveryMan = await DeliveryManService.getDeliveryManByclientId(clientId);
        if (!deliveryMan) return alert("No se encontró información del repartidor.");

        await DeliveryPointService.updateDeliveryManId(
          deliveryPointId,
          deliveryMan.deliveryman_id
        );
        alert("Orden aceptada con éxito.");
        this.fetchOrders();
      } catch (error) {
        console.error("Error al aceptar la orden:", error.response?.data || error.message);
        alert("No se pudo aceptar la orden. Intenta nuevamente.");
      }
    },
  },
  mounted() {
    this.fetchOrders();
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

h2,
h3 {
  color: #333;
  margin-bottom: 20px;
  text-align: center;
}

.table th,
.table td {
  text-align: center;
  vertical-align: middle;
}

.btn-primary {
  background-color: #007bff;
  border: none;
  transition: background-color 0.3s ease;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-info {
  background-color: #17a2b8;
  border: none;
  transition: background-color 0.3s ease;
}

.btn-info:hover {
  background-color: #138496;
}
</style>
