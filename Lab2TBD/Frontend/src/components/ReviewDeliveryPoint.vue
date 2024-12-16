<template>
    <div class="map-container">
      <h2 class="text-center mb-4">Reseña de Ubicación</h2>
  
      <!-- Botón para volver atrás -->
      <button @click="goBack" class="btn btn-secondary mb-3">← Volver</button>
  
      <!-- Mapa -->
      <GMapMap
        :center="mapCenter"
        :zoom="15"
        style="height: 500px; width: 100%"
      >
        <!-- Marcador -->
        <GMapMarker :position="mapCenter" />
      </GMapMap>
  
      <!-- Información de la ubicación -->
      <div v-if="deliveryPoint" class="location-details mt-4">
        <h4>Detalles del Punto de Entrega</h4>
        <p><strong>Dirección:</strong> {{ deliveryPoint.address || "No especificada" }}</p>
        <p><strong>Coordenadas:</strong> {{ mapCenter.lat }}, {{ mapCenter.lng }}</p>
      </div>
  
      <!-- Formulario de reseña -->
      <div class="review-section mt-4">
        <h4 class="text-center">Reseña del Punto de Entrega</h4>
        <p class="text-center">
          Puntúa la seguridad, facilidad de entrega y otros aspectos relevantes del lugar.
        </p>
        <form @submit.prevent="submitReview">
          <!-- Calificación -->
          <div class="rating mb-3 text-center">
            <label for="rating"><strong>Puntaje (0-5 estrellas):</strong></label>
            <select v-model="review.rating" id="rating" class="form-control">
              <option v-for="n in 6" :key="n" :value="n - 1">{{ n - 1 }} ★</option>
            </select>
          </div>
  
          <!-- Comentario -->
          <div class="form-group">
            <label for="comment"><strong>Comentarios:</strong></label>
            <textarea
              v-model="review.comment"
              id="comment"
              rows="3"
              class="form-control"
              placeholder="Describe tu experiencia en este punto de entrega..."
            ></textarea>
          </div>
  
          <!-- Botón de enviar -->
          <div class="text-center mt-3">
            <button type="submit" class="btn btn-primary">Guardar Reseña</button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import DeliveryPointService from "@/services/deliverypoint.service";
  
  export default {
    name: "DeliveryPointViewer",
    props: {
      deliveryPointId: {
        type: Number,
        required: true, // ID del punto de entrega a mostrar
      },
    },
    data() {
      return {
        mapCenter: { lat: 0, lng: 0 }, // Coordenadas iniciales del mapa
        deliveryPoint: null, // Detalles del punto de entrega
        review: {
          rating: 0, // Puntaje inicial
          comment: "", // Comentario
        },
      };
    },
    methods: {
      async fetchDeliveryPoint() {
        try {
          const deliveryPoint = await DeliveryPointService.getDeliveryPointById(this.deliveryPointId);
          // Actualizar las coordenadas del mapa y los detalles del punto de entrega
          this.mapCenter = {
            lat: parseFloat(deliveryPoint.latitude),
            lng: parseFloat(deliveryPoint.longitude),
          };
          this.deliveryPoint = deliveryPoint;
        } catch (error) {
          console.error("Error al obtener el punto de entrega:", error);
          alert("No se pudo cargar el punto de entrega.");
        }
      },
      async submitReview() {
        try {
          // Llamar al método reviewDeliveryPoint en el servicio
          await DeliveryPointService.reviewDeliveryPoint(this.deliveryPointId, this.review);
          alert("¡Reseña guardada exitosamente!");
          // Limpiar el formulario
          this.review.rating = 0;
          this.review.comment = "";
        } catch (error) {
          console.error("Error al guardar la reseña:", error);
          alert("No se pudo guardar la reseña.");
        }
      },
      goBack() {
        this.$router.go(-1); // Ir a la página anterior
      },
    },
    mounted() {
      this.fetchDeliveryPoint();
    },
  };
  </script>
  
  <style scoped>
  .map-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f8f9fa;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .btn {
    display: block;
    margin: 0 auto 20px;
    font-size: 1rem;
  }
  
  .location-details,
  .review-section {
    text-align: center;
    background-color: #ffffff;
    padding: 15px;
    border-radius: 10px;
    border: 1px solid #ddd;
  }
  
  .location-details h4,
  .review-section h4 {
    margin-bottom: 15px;
  }
  
  .review-section form {
    max-width: 600px;
    margin: 0 auto;
  }
  
  .form-group textarea {
    resize: none;
  }
  </style>
  