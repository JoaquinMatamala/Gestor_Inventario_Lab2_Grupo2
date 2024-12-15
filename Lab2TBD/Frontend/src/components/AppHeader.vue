<template>
  <header>
    <nav>
      <button @click="goHome" class="nav-button">Inicio</button>
      <button @click="goToBuy" class="nav-button">Ver productos</button>
      <button @click="goToTasks" class="nav-button">Mis Órdenes</button>
      <button @click="goToRanking" class="nav-button">Ver Top Globales</button>
    </nav>
    <h1 class="centered-title">Gestor de Inventario</h1>
    <div class="right-buttons">
      <button @click="goToAddress" class="address-button">Dirección de entrega</button>
      <button @click="logout" class="logout-button">Cerrar Sesión</button>
    </div>
  </header>
</template>

<script>
import ClientService from "@/services/client.service";

export default {
  name: "AppHeader",
  methods: {
    async goHome() {
      this.$router.push("/"); // Redirige a la página de inicio
    },
    async goToBuy() {
      const isValid = await ClientService.validateSession(); // Valida la sesión
      if (isValid) {
        this.$router.push("/products"); // Redirige al menú de cliente
      }
    },
    async goToTasks() {
      const isValid = await ClientService.validateSession(); // Valida la sesión
      if (isValid) {
        this.$router.push("/clientpage/orders");
      }
    },
    async goToRanking() {
      const isValid = await ClientService.validateSession(); // Valida la sesión
      if (isValid) {
        this.$router.push("/ranking-queries"); //
      }
    },
    async goToAddress() {
      const isValid = await ClientService.validateSession(); // Valida la sesión
      if (isValid) {
        this.$router.push("/select-location"); // Cambia esta ruta según tu implementación
      }
    },
    logout() {
      localStorage.removeItem("jwtToken"); // Limpia el token del almacenamiento
      localStorage.removeItem("userId"); // Limpia el ID del usuario
      this.$router.push("/"); // Redirige a la página principal
    },
  },
};
</script>

<style scoped>
header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #007bff;
  color: white;
  padding: 15px 0;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
  z-index: 10;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.centered-title {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 2rem;
  margin: 0;
}

nav {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-left: 20px;
}

.nav-button {
  background-color: white;
  color: #007bff;
  border: 1px solid #007bff;
  padding: 10px 15px;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

.nav-button:hover {
  background-color: #0056b3;
  color: white;
}

.right-buttons {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-right: 20px;
}

.address-button {
  background-color: #6c757d; /* Botón gris */
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.address-button:hover {
  background-color: #5a6268; /* Gris más oscuro al pasar */
}

.logout-button {
  background-color: #dc3545;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.logout-button:hover {
  background-color: #b02a37;
}
</style>
