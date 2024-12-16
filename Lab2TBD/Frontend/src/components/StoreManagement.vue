<template>
  <div class="store-management-container">
    <h2 class="text-center mb-4">Administrar Tiendas</h2>

    <ul class="list-group mb-4">
      <li
        class="list-group-item d-flex justify-content-between align-items-center"
        v-for="store in stores"
        :key="store.establishment_id"
      >
        <div>
          <strong>{{ store.establishment_data }}</strong><br />
          <span v-if="store.address">{{ store.address }}</span>
          <span v-else>Cargando dirección...</span>
        </div>
        <!-- Botón "Ver en mapa" -->
        <button class="btn btn-primary" @click="viewOnMap(store.location_id)">
          Ver en mapa
        </button>
      </li>
    </ul>

    <button @click="goToSelectLocation" class="btn btn-primary w-100">Añadir Nueva Tienda</button>
  </div>
</template>

<script>
import EstablishmentService from "@/services/establishment.service";

export default {
  name: "StoreManagement",
  data() {
    return {
      stores: [], // Lista de establecimientos
    };
  },
  methods: {
    async fetchStores() {
      try {
        const establishments = await EstablishmentService.getAllEstablishments();

        // Añadir direcciones obtenidas por location_id
        this.stores = await Promise.all(
          establishments.map(async (store) => {
            try {
              const address = await EstablishmentService.getAddressByLocationId(store.location_id);
              return { ...store, address };
            } catch (error) {
              console.error(`Error al obtener dirección para location_id ${store.location_id}:`, error.message);
              return { ...store, address: "Error al cargar dirección" };
            }
          })
        );
      } catch (error) {
        console.error("Error al obtener los establecimientos:", error.message);
        alert("Hubo un error al cargar las tiendas.");
      }
    },
    goToSelectLocation() {
      this.$router.push("/admin/new-establishment");
    },
    viewOnMap(locationId) {
      // Redirigir a la ruta existente con el parámetro como query string
      this.$router.push(`/location-viewer?locationId=${locationId}`);
    },
  },
  mounted() {
    this.fetchStores();
  },
};
</script>

<style scoped>
.store-management-container {
  max-width: 800px;
  margin: 50px auto;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
}

.list-group-item {
  font-size: 1.1rem;
}

.btn {
  font-size: 1.2rem;
}
</style>
