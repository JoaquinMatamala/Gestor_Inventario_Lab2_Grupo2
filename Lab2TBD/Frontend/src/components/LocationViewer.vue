<template>
  <div class="map-container">
    <h2 class="text-center mb-4">Visor de Ubicación</h2>

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
    <div v-if="location" class="location-details mt-4">
      <h4>Detalles de la Ubicación</h4>
      <p><strong>Dirección:</strong> {{ location.address || "No especificada" }}</p>
      <p><strong>Coordenadas:</strong> {{ mapCenter.lat }}, {{ mapCenter.lng }}</p>
    </div>
  </div>
</template>

<script>
import LocationService from "@/services/location.service";

export default {
  name: "LocationViewer",
  props: {
    locationId: {
      type: Number,
      required: true, // ID de la ubicación a mostrar
    },
  },
  data() {
    return {
      mapCenter: { lat: 0, lng: 0 }, // Coordenadas iniciales del mapa
      location: null, // Detalles de la ubicación
    };
  },
  methods: {
    async fetchLocation() {
      try {
        const location = await LocationService.getLocationById(this.locationId);

        // Actualizar las coordenadas del mapa y los detalles de la ubicación
        this.mapCenter = {
          lat: parseFloat(location.latitude),
          lng: parseFloat(location.longitude),
        };
        this.location = location; // Guardar detalles de la ubicación
      } catch (error) {
        console.error("Error al obtener la ubicación:", error);
        alert("No se pudo cargar la ubicación.");
      }
    },
    goBack() {
      this.$router.go(-1); // Ir a la página anterior
    },
  },
  mounted() {
    this.fetchLocation();
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

.location-details {
  text-align: center;
  background-color: #ffffff;
  padding: 15px;
  border-radius: 10px;
  border: 1px solid #ddd;
}

.location-details h4 {
  margin-bottom: 15px;
}

.location-details p {
  margin: 5px 0;
  font-size: 1rem;
  color: #333;
}
</style>
