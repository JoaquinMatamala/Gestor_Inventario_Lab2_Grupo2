<template>
    <div class="store-management-container">
      <h2 class="text-center mb-4">Administrar Tiendas</h2>
      <ul class="list-group mb-4">
        <li class="list-group-item" v-for="store in stores" :key="store.id">
          {{ store.name }} - {{ store.address }}
        </li>
      </ul>
      <button @click="goToSelectLocation" class="btn btn-primary w-100">Añadir Nueva Tienda</button>
    </div>
  </template>
  
  <script>
  export default {
    name: "StoreManagement",
    data() {
      return {
        stores: [
          { id: 1, name: "Tienda 1", address: "Calle Falsa 123" },
          { id: 2, name: "Tienda 2", address: "Calle Verdadera 456" },
        ], // Lista simulada, reemplazar con datos reales
      };
    },
    methods: {
      goToSelectLocation() {
        this.$router.push("/select-location");
      },
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
  