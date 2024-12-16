<template>
    <div class="data-container">
      <h2 class="text-center mb-4">Mis Entregas Pendientes</h2>
  
      <!-- Tabla de entregas -->
      <div v-if="deliveryPoints.length > 0">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>ID Punto de Entrega</th>
              <th>Cliente</th>
              <th>Dirección</th>
              <th>Comentario</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="point in deliveryPoints" :key="point.delivery_point_id">
              <td>{{ point.delivery_point_id }}</td>
              <td>{{ point.client_name || "Desconocido" }}</td>
              <td>
                <button
                  class="btn btn-primary btn-sm"
                  @click="viewOnMap(point.delivery_location_point)"
                >
                  Ver en mapa
                </button>
              </td>
              <td>{{ point.comment || "Sin comentarios" }}</td>
              <td>
                <button
                  class="btn btn-success btn-sm"
                  @click="completeAndEvaluate(point.delivery_point_id)"
                >
                  Completar y evaluar
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
  
      <div v-else class="text-center">
        <p>No tienes entregas pendientes.</p>
      </div>
  
      <button class="btn btn-primary w-100 mt-3" @click="fetchDeliveries">
        Actualizar Entregas
      </button>
    </div>
  </template>
  
  <script>
  import DeliveryManService from "@/services/deliveryman.service";
  import DeliveryPointService from "@/services/deliverypoint.service";
  import ClientService from "@/services/client.service";
  
  export default {
    name: "ToDoDeliveries",
    data() {
      return {
        deliveryPoints: [], // Lista de entregas
      };
    },
    methods: {
      // Obtener todas las entregas asociadas al repartidor
      async fetchDeliveries() {
        try {
          const clientId = localStorage.getItem("clientId");
          if (!clientId) {
            alert("Usuario no autenticado.");
            return;
          }
  
          // Obtener el deliveryManId usando el clientId
          const deliveryMan = await DeliveryManService.getDeliveryManByclientId(clientId);
          if (!deliveryMan || !deliveryMan.deliveryman_id) {
            alert("No se encontró un repartidor asociado.");
            return;
          }
  
          // Obtener los DeliveryPoints asociados al deliveryManId
          const points = await DeliveryPointService.getDeliveryPointsByDeliveryManId(
            deliveryMan.deliveryman_id
          );
  
          // Enriquecer los datos con información adicional si es necesario
          this.deliveryPoints = await Promise.all(
            points.map(async (point) => {
              try {
                const clientName = await ClientService.getClientName(point.client_id);
                return { ...point, client_name: clientName };
              } catch (error) {
                console.error(
                  `Error al obtener nombre del cliente para ${point.delivery_point_id}:`,
                  error.message
                );
                return { ...point, client_name: "Desconocido" };
              }
            })
          );
        } catch (error) {
          console.error("Error al obtener las entregas pendientes:", error.response?.data || error.message);
          alert("No se pudieron cargar las entregas. Intenta nuevamente.");
        }
      },
  
      // Redirigir al mapa
      async viewOnMap(deliveryLocationPoint) {
        try {
          if (!deliveryLocationPoint) {
            alert("No se encontró una ubicación para esta entrega.");
            return;
          }
          this.$router.push(`/location-viewer?locationId=${deliveryLocationPoint}`);
        } catch (error) {
          console.error("Error al redirigir al mapa:", error.response?.data || error.message);
          alert("No se pudo redirigir al mapa. Intenta nuevamente.");
        }
      },
  
      // Completar y evaluar la entrega
      async completeAndEvaluate(deliveryPointId) {
        try {
          const confirmed = confirm("¿Estás seguro de completar y evaluar esta entrega?");
          if (!confirmed) return;
  
          // Placeholder para lógica adicional al completar la entrega
          // Aquí puedes llamar a un servicio para actualizar el estado de la entrega y guardar la evaluación
  
          alert(`Entrega con ID ${deliveryPointId} completada y lista para evaluar.`);
          this.fetchDeliveries(); // Actualizar la lista después de completar
        } catch (error) {
          console.error(
            `Error al completar y evaluar la entrega ${deliveryPointId}:`,
            error.response?.data || error.message
          );
          alert("No se pudo completar la entrega. Intenta nuevamente.");
        }
      },
    },
    mounted() {
      this.fetchDeliveries();
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
  
  .btn-success {
    background-color: #28a745;
    border: none;
    transition: background-color 0.3s ease;
  }
  
  .btn-success:hover {
    background-color: #218838;
  }
  </style>