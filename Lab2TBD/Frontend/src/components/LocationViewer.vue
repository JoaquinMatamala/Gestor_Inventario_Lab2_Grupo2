<template>
    <div class="map-container">
      <h2 class="text-center mb-4">Visor de Ubicación</h2>
  
      <!-- Detalles de la ubicación -->
      <div v-if="location">
        <h4>Detalles de la Ubicación</h4>
        <p>
          <strong>Dirección:</strong> {{ location.address || "No especificado" }} <br />
          <strong>Coordenadas:</strong> {{ location.latitude }}, {{ location.longitude }} <br />
          <strong>Tipo:</strong> {{ location.location_type || "No especificado" }}
        </p>
      </div>
      <p v-else>Obteniendo los detalles de la ubicación...</p>
  
      <!-- Mapa -->
      <GMapMap
        :center="mapCenter"
        :zoom="15"
        style="height: 500px; width: 100%"
        :options="mapOptions"
      >
        <GMapMarker :position="mapCenter" />
      </GMapMap>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "LocationViewer",
    props: {
      locationId: {
        type: Number,
        required: true, // El ID de la ubicación a mostrar
      },
    },
    data() {
      return {
        location: null, // Datos de la ubicación obtenidos del backend
        mapCenter: { lat: 0, lng: 0 }, // Coordenadas iniciales del mapa
        mapOptions: {
          scrollwheel: true, // Permite hacer zoom sin presionar Ctrl
          disableDefaultUI: true, // Opcional: Oculta controles predeterminados
        },
      };
    },
    methods: {
      async fetchLocation() {
        try {
          const response = await axios.get(
            `${process.env.VUE_APP_BACKEND_IP}/api/locations/getLocation/${this.locationId}`
          );
  
          this.location = response.data;
  
          // Configurar el centro del mapa usando latitude y longitude
          this.mapCenter = {
            lat: parseFloat(this.location.latitude),
            lng: parseFloat(this.location.longitude),
          };
        } catch (error) {
          console.error("Error al obtener la ubicación:", error.response?.data || error.message);
          alert("Hubo un error al cargar la ubicación. Intenta nuevamente.");
        }
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
  
  h2 {
    color: #333;
  }
  
  p {
    font-size: 1rem;
    line-height: 1.6;
  }
  </style>
  