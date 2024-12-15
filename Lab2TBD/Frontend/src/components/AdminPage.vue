<template>
    <div class="admin-container">
      <h2 class="text-center mb-4">Panel de Administrador</h2>
      <div class="button-group text-center">
        <button @click="goToStores" class="btn btn-primary btn-lg mb-3">Administrar Tiendas</button>
        <button @click="goToDelivery" class="btn btn-secondary btn-lg">Administrar Repartidores</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "AdminPage",
    methods: {
      goToStores() {
        this.$router.push("/admin/stores");
      },
      goToDelivery() {
        this.$router.push("/admin/delivery");
      },
    },
  };
  </script>
  
  <style scoped>
  .admin-container {
    max-width: 600px;
    margin: 50px auto;
    padding: 2rem;
    border-radius: 8px;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    background-color: #f9f9f9;
  }
  
  .button-group .btn {
    width: 100%;
    margin-bottom: 1rem;
    font-size: 1.2rem;
    font-weight: bold;
  }
  </style>
  