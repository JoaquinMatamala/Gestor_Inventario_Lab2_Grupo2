<template>
    <div class="delivery-container">
      <h2 class="text-center mb-4">Panel de Repartidor</h2>
      <div class="button-group text-center">
        <button @click="goToDeliveryPage" class="btn btn-primary btn-lg mb-3">Revisar órdenes</button>
        <button @click="goToVerifyAddresses" class="btn btn-primary btn-lg mb-3">Review de punto de entrega</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "DeliveryMainPage",
    methods: {
      goToDeliveryPage() {
        this.$router.push("/del-page/deliveryman");
      },
      goToVerifyAddresses() {
        this.$router.push("/del-page/review-point");
      },
    },
  };
  </script>
  
  <style scoped>
  .delivery-container {
    max-width: 600px;
    margin: 50px auto;
    padding: 2rem;
    border-radius: 8px;
    box-shadow: 0px 4px 8px rgba(7, 7, 7, 0.1);
    background-color: #f9f9f9;
  }
  
  .button-group .btn {
    width: 100%;
    margin-bottom: 1rem;
    font-size: 1.2rem;
    font-weight: bold;
  }
  </style>
  