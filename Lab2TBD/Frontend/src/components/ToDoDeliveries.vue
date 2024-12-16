<template>
    <div class="data-container">
      <h2 class="text-center mb-4">Mis Entregas Pendientes</h2>
  
      <!-- Tabla de entregas -->
      <div v-if="filteredDeliveryPoints.length > 0">
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
            <tr v-for="point in filteredDeliveryPoints" :key="point.delivery_point_id">
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
                  @click="openReviewModal(point.delivery_point_id)"
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
  
      <!-- Modal para evaluación -->
      <div v-if="showModal" class="modal fade show" tabindex="-1" role="dialog" style="display: block; background: rgba(0,0,0,0.5);">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Evaluar Entrega</h5>
              <button type="button" class="btn-close" @click="closeReviewModal"></button>
            </div>
            <div class="modal-body">
              <div class="form-group">
                <label for="rating">Cantidad de Estrellas:</label>
                <select v-model="review.rating" id="rating" class="form-control">
                  <option v-for="n in 5" :key="n" :value="n">{{ n }} ★</option>
                </select>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" @click="closeReviewModal">Cerrar</button>
              <button type="button" class="btn btn-primary" @click="confirmReview">Confirmar</button>
            </div>
          </div>
        </div>
      </div>
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
        showModal: false, // Estado del modal
        review: {
          rating: 1, // Puntaje inicial
        },
        currentDeliveryPointId: null, // ID del punto de entrega actual
      };
    },
    computed: {
      filteredDeliveryPoints() {
        return this.deliveryPoints.filter(point => point.rating === null);
      }
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
  
      // Abrir el modal de evaluación
      openReviewModal(deliveryPointId) {
        this.currentDeliveryPointId = deliveryPointId;
        this.showModal = true;
      },
  
      // Cerrar el modal de evaluación
      closeReviewModal() {
        this.showModal = false;
        this.review.rating = 1;
        this.currentDeliveryPointId = null;
      },
  
      // Confirmar la evaluación
      async confirmReview() {
        try {
          // Llamar al servicio para actualizar el rating
          await DeliveryPointService.updateRating(this.currentDeliveryPointId, this.review.rating);
          console.log(`Evaluación para el punto de entrega ${this.currentDeliveryPointId}: ${this.review.rating} estrellas`);
          alert("Evaluación guardada exitosamente.");
        } catch (error) {
          console.error("Error al guardar la evaluación:", error.response?.data || error.message);
          alert("No se pudo guardar la evaluación. Intenta nuevamente.");
        }
  
        this.closeReviewModal();
        this.fetchDeliveries(); // Actualizar la lista después de completar
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
  
  .modal-content {
    background-color: #fff;
    border-radius: 10px;
    overflow: hidden;
  }
  
  .modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .modal-footer {
    display: flex;
    justify-content: flex-end;
    padding: 10px;
  }
  
  .modal-body .form-group {
    margin-bottom: 15px;
  }
  
  .modal-body .form-control {
    width: 100%;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
  }
  </style>