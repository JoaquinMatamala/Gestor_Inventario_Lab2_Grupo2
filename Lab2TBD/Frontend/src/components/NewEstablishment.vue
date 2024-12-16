<template>
    <div class="map-container">
      <h2 class="text-center mb-4">{{ isAdmin ? 'Crear un Nuevo Establecimiento' : 'Selecciona una Ubicación' }}</h2>
  
      <!-- Formulario solo para ADMIN -->
      <div v-if="isAdmin">
        <!-- Nombre del Establecimiento -->
        <div class="form-group">
          <label for="establishmentName">Nombre del Establecimiento:</label>
          <input
            v-model="establishmentData.establishment_data"
            type="text"
            id="establishmentName"
            placeholder="Ingresa el nombre"
            class="form-control"
          />
        </div>
  
        <!-- Región de Chile -->
        <div class="form-group">
          <label for="region">Región Detectada:</label>
          <input
            v-model="establishmentData.region_data"
            type="text"
            id="region"
            class="form-control"
            placeholder="Región"
            readonly
          />
        </div>
      </div>
  
      <!-- Buscador de Direcciones -->
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
  
      <!-- Mapa -->
      <GMapMap
        :center="center"
        :zoom="12"
        style="height: 500px; width: 100%"
        :options="mapOptions"
        @click="addMarker"
      >
        <GMapMarker v-for="(marker, index) in markers" :key="index" :position="marker" />
      </GMapMap>
  
      <!-- Detalles de la Ubicación -->
      <div class="location-details mt-4">
        <h4>Detalles de la Ubicación Seleccionada</h4>
        <p v-if="geoJson">
          <strong>Coordenadas:</strong> {{ geoJson.geometry.coordinates.join(", ") }} <br />
          <strong>Dirección:</strong> {{ geoJson.properties.address || "No especificado" }} <br />
          <strong>Región:</strong> {{ establishmentData.region_data || "No detectada" }}
        </p>
        <p v-else>No se ha seleccionado ninguna ubicación aún.</p>
  
        <!-- Botón para enviar -->
        <button
          class="btn btn-primary w-100 mt-3"
          @click="createLocationAndEstablishment"
          :disabled="!geoJson || !establishmentData.establishment_data || !establishmentData.region_data"
        >
          Crear Establecimiento
        </button>
      </div>
    </div>
  </template>
  
  <script>
  import LocationService from "@/services/location.service";
  import EstablishmentService from "@/services/establishment.service";
  
  export default {
    name: "NewEstablishment",
    data() {
      return {
        center: { lat: -33.4569, lng: -70.6483 },
        markers: [],
        geoJson: null,
        addressSearch: "",
        establishmentData: {
          establishment_data: "",
          region_data: "",
          location_id: null,
        },
        mapOptions: {
          scrollwheel: true,
          gestureHandling: "greedy",
        },
      };
    },
    computed: {
      isAdmin() {
        return localStorage.getItem("role") === "ADMIN";
      },
    },
    methods: {
      async addMarker(event) {
        const { latLng } = event;
        const position = { lat: latLng.lat(), lng: latLng.lng() };
  
        this.markers = [position];
        const addressDetails = await this.getAddressFromCoordinates(position.lat, position.lng);
  
        if (addressDetails) {
          this.geoJson = {
            geometry: {
              type: "Point",
              coordinates: [position.lng, position.lat],
            },
            properties: {
              address: addressDetails.formattedAddress || "No especificado",
            },
          };
          this.establishmentData.region_data = addressDetails.region || "";
        }
      },
  
      async searchAddress() {
        if (!this.addressSearch.trim()) return;
        const apiKey = process.env.VUE_APP_GOOGLE_MAPS_API_KEY;
  
        const response = await fetch(
          `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(
            this.addressSearch
          )}&key=${apiKey}`
        );
        const data = await response.json();
  
        if (data.results.length > 0) {
          const location = data.results[0].geometry.location;
          this.center = { lat: location.lat, lng: location.lng };
          this.markers = [{ lat: location.lat, lng: location.lng }];
          this.geoJson = {
            geometry: { type: "Point", coordinates: [location.lng, location.lat] },
            properties: { address: data.results[0].formatted_address },
          };
  
          this.establishmentData.region_data = this.extractRegion(data.results[0].address_components);
        }
      },
  
      async getAddressFromCoordinates(lat, lng) {
        const apiKey = process.env.VUE_APP_GOOGLE_MAPS_API_KEY;
  
        const response = await fetch(
          `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${apiKey}`
        );
        const data = await response.json();
  
        if (data.results.length > 0) {
          const addressComponents = data.results[0].address_components;
          const region = this.extractRegion(addressComponents);
  
          return {
            formattedAddress: data.results[0].formatted_address,
            region: region || "",
          };
        }
        return null;
      },
  
      extractRegion(components) {
        const regionComponent = components.find((component) =>
          component.types.includes("administrative_area_level_1")
        );
        return regionComponent ? regionComponent.long_name : null;
      },
  
      async createLocationAndEstablishment() {
        try {
          await LocationService.saveLocation(this.geoJson);
          const location = await LocationService.getLocationWithMaxId();
  
          this.establishmentData.location_id = location.location_id;
  
          await EstablishmentService.buildEstablishment(this.establishmentData);
  
          alert("Establecimiento y ubicación creados con éxito.");
          this.$router.push("/admin"); // Redirigir al panel de administrador
        } catch (error) {
          console.error("Error al crear la ubicación/establecimiento:", error);
          alert("Hubo un error. Intenta nuevamente.");
        }
      },
  
      resetForm() {
        this.geoJson = null;
        this.markers = [];
        this.establishmentData = {
          establishment_data: "",
          region_data: "",
          location_id: null,
        };
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
  .input-group {
    display: flex;
    gap: 10px;
    margin-bottom: 15px;
  }
  </style>
  