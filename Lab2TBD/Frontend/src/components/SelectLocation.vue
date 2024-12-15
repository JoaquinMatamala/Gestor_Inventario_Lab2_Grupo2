<template>
  <div class="map-container">
    <h2 class="text-center mb-4">Selecciona una Ubicación en el Mapa</h2>

    <!-- Buscador de direcciones -->
    <div class="form-group">
      <label for="addressSearch">Buscar Dirección:</label>
      <div class="input-group">
        <input
          type="text"
          id="addressSearch"
          v-model="addressSearch"
          placeholder="Ingresa una dirección"
          class="form-control"
        />
        <button @click="searchAddress" class="btn btn-primary">Buscar</button>
      </div>
    </div>

    <!-- Mapa con Google Maps -->
    <GMapMap
      :center="center"
      :zoom="12"
      style="height: 500px; width: 100%"
      :options="mapOptions"
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
      center: { lat: -33.4569, lng: -70.6483 },
      markers: [],
      geoJson: null,
      locationType: "home",
      addressSearch: "",
      mapOptions: {
        scrollwheel: true,
      },
    };
  },
  methods: {
    async addMarker(event) {
      const { latLng } = event;

      const position = {
        lat: latLng.lat(),
        lng: latLng.lng(),
      };

      this.markers = [position];

      const address = await this.getAddressFromCoordinates(position.lat, position.lng);

      this.geoJson = {
        geometry: {
          type: "Point",
          coordinates: [position.lng, position.lat],
        },
        properties: {
          location_type: this.locationType,
          address: address || "No especificado",
        },
      };
    },

    async searchAddress() {
      if (!this.addressSearch.trim()) {
        alert("Por favor, ingresa una dirección.");
        return;
      }

      const apiKey = process.env.VUE_APP_GOOGLE_MAPS_API_KEY;
      try {
        const response = await fetch(
          `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(
            this.addressSearch
          )}&key=${apiKey}`
        );
        const data = await response.json();

        if (data.results && data.results.length > 0) {
          const result = data.results[0];
          const location = result.geometry.location;

          this.center = { lat: location.lat, lng: location.lng };
          this.markers = [{ lat: location.lat, lng: location.lng }];
          this.geoJson = {
            geometry: {
              type: "Point",
              coordinates: [location.lng, location.lat],
            },
            properties: {
              location_type: this.locationType,
              address: result.formatted_address,
            },
          };
        } else {
          alert("No se encontró ninguna ubicación para la dirección ingresada.");
        }
      } catch (error) {
        console.error("Error al buscar la dirección:", error);
        alert("Hubo un error al buscar la dirección. Intenta nuevamente.");
      }
    },

    async getAddressFromCoordinates(lat, lng) {
      const apiKey = process.env.VUE_APP_GOOGLE_MAPS_API_KEY;
      try {
        const response = await fetch(
          `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${apiKey}`
        );
        const data = await response.json();

        if (data.results && data.results.length > 0) {
          return data.results[0].formatted_address;
        }
        return null;
      } catch (error) {
        console.error("Error al obtener la dirección:", error);
        return null;
      }
    },

    async sendGeoJson() {
      if (!this.geoJson) {
        alert("Selecciona una ubicación en el mapa primero.");
        return;
      }

      try {
        const role = localStorage.getItem("role");
        const clientId = localStorage.getItem("clientId");

        // Crear la ubicación
        await LocationService.saveLocation(this.geoJson);
        alert("✅ Ubicación creada con éxito.");

        // Obtener el ID de la ubicación creada
        const location = await LocationService.getLocationWithMaxId();
        const locationId = location.location_id;
        console.log("📥 Última ubicación creada:", location);

        // Asignar la ubicación al cliente si es necesario
        if (role === "CLIENTE" && clientId) {
          await LocationService.assignHomeLocation(clientId, locationId);
          alert(`✅ Ubicación asignada correctamente al cliente con ID: ${clientId}`);
        } else {
          alert("El rol no es CLIENTE. Solo se creó la ubicación.");
        }

        this.markers = [];
        this.geoJson = null;
      } catch (error) {
        console.error("Error en el flujo de creación/asignación:", error.response?.data || error.message);
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

.input-group {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}
</style>
