<template>
  <div class="map-container">
    <h2 class="text-center mb-4">Selecciona una Ubicación en el Mapa</h2>
    <!-- Mapa con Google Maps -->
    <GMapMap
      :center="center"
      :zoom="12"
      style="height: 500px; width: 100%"
      @click="addMarker"
    >
      <GMapMarker
        v-for="(marker, index) in markers"
        :key="index"
        :position="marker"
      />
    </GMapMap>

    <!-- Detalles de la ubicación -->
    <div class="location-details mt-4">
      <h4>Detalles de la Ubicación Seleccionada</h4>
      <p v-if="geoJson">
        <strong>Coordenadas:</strong> {{ geoJson.geometry.coordinates.join(", ") }} <br />
        <strong>Dirección:</strong> {{ geoJson.properties.address || "No especificado" }} <br />
        <strong>Tipo:</strong> {{ geoJson.properties.location_type || "No especificado" }}
      </p>
      <p v-else>No se ha seleccionado ninguna ubicación aún.</p>

      <!-- Selector para el tipo de ubicación -->
      <div class="form-group mt-3">
        <label for="locationType">Tipo de Ubicación:</label>
        <select v-model="locationType" id="locationType" class="form-control">
          <option value="home">Hogar</option>
          <option value="office">Oficina</option>
          <option value="store">Tienda</option>
        </select>
      </div>

      <!-- Botón para enviar la ubicación -->
      <button
        class="btn btn-primary w-100 mt-3"
        @click="sendGeoJson"
        :disabled="!geoJson"
      >
        Enviar Ubicación
      </button>
    </div>
  </div>
</template>

<script>
import LocationService from "@/services/location.service";

export default {
  name: "SelectLocation",
  data() {
    return {
      center: { lat: -33.4569, lng: -70.6483 }, // Centro del mapa (Santiago, Chile)
      markers: [], // Lista de marcadores
      geoJson: null, // Datos GeoJSON de la ubicación seleccionada
      locationType: "home", // Tipo de ubicación seleccionada
    };
  },
  methods: {
    // Agregar marcador y obtener la dirección automáticamente
    async addMarker(event) {
      const { latLng } = event;

      // Crear posición del marcador
      const position = {
        lat: latLng.lat(),
        lng: latLng.lng(),
      };

      // Actualizar lista de marcadores (único marcador)
      this.markers = [position];

      // Obtener dirección con la Geocoding API
      const address = await this.getAddressFromCoordinates(position.lat, position.lng);

      // Crear el GeoJSON
      this.geoJson = {
        geometry: {
          type: "Point",
          coordinates: [position.lng, position.lat], // Formato GeoJSON
        },
        properties: {
          location_type: this.locationType,
          address: address || "No especificado",
        },
      };
    },

    // Método para obtener la dirección usando la Geocoding API
    async getAddressFromCoordinates(lat, lng) {
      const apiKey = ""; // Reemplaza con tu clave de API de Google Maps
      try {
        const response = await fetch(
          `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${apiKey}`
        );
        const data = await response.json();

        if (data.results && data.results.length > 0) {
          return data.results[0].formatted_address; // Extraer la dirección
        }
        return null;
      } catch (error) {
        console.error("Error al obtener la dirección:", error);
        return null;
      }
    },

    // Método para enviar el GeoJSON al backend
    async sendGeoJson() {
      if (!this.geoJson) {
        alert("Selecciona una ubicación en el mapa primero.");
        return;
      }

      try {
        console.log("GeoJSON enviado:", JSON.stringify(this.geoJson));
        await LocationService.saveLocation(this.geoJson);
        alert("Ubicación enviada correctamente.");
        this.markers = []; // Limpiar marcador
        this.geoJson = null; // Reiniciar GeoJSON
      } catch (error) {
        console.error("Error al enviar el GeoJSON:", error.response?.data || error.message);
        alert("Hubo un error al enviar la ubicación. Intenta nuevamente.");
      }
    },
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

button[disabled] {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>
