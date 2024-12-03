<template>
  <header>
    <nav>
      <button @click="goHome" class="nav-button">Inicio</button>
      <button @click="goToBuy" class="nav-button">Ver productos</button>
      <button @click="goToTasks" class="nav-button">Mis Órdenes</button>
      <button @click="goToRanking" class="nav-button">Ver Top Globales</button>
    </nav>
    <h1 class="centered-title">Gestor de Inventario</h1>
    

    <button @click="logout" class="logout-button">Cerrar Sesión</button>
  </header>
</template>

<script>
import { validateSession } from "@/services/client.service";

export default {
  name: "AppHeader",
  methods: {
    async goHome() {
      this.$router.push("/"); // Redirige a la página de inicio
    },
    async goToBuy() {
      const isValid = await validateSession(); // Valida la sesión
      if (isValid) {
        this.$router.push("/products"); // Redirige al menú de cliente
      }
    },
    async goToTasks() {
      const isValid = await validateSession(); // Valida la sesión
      if (isValid) {
        this.$router.push("/clientpage/orders");
      }
    },
    async goToRanking() {
      const isValid = await validateSession(); // Valida la sesión
      if (isValid) {
        this.$router.push("/ranking-queries"); // 
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
  position: fixed; /* Fija el header en la parte superior */
  top: 0; /* Alinea al borde superior */
  left: 0; /* Alinea al borde izquierdo */
  width: 100%; /* Asegura que ocupe todo el ancho */
  background-color: #007bff; /* Fondo azul */
  color: white; /* Texto blanco */
  padding: 15px 0; /* Espaciado interno vertical */
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1); /* Sombra sutil */
  z-index: 10; /* Mantén el header sobre otros elementos */
  display: flex; /* Habilitar flexbox */
  justify-content: space-between; /* Espaciado entre los elementos del header */
  align-items: center; /* Alinear elementos verticalmente */
}

.centered-title {
  position: absolute; /* Posiciona el título dentro del header */
  top: 50%; /* Centra verticalmente */
  left: 50%; /* Centra horizontalmente */
  transform: translate(-50%, -50%); /* Ajusta la posición al centro exacto */
  font-size: 2rem; /* Tamaño del título */
  margin: 0; /* Sin margen adicional */
}

nav {
  display: flex;
  align-items: center;
  gap: 10px; /* Espaciado entre botones */
  margin-left: 20px;
}

.nav-button {
  background-color: white; /* Fondo blanco para los botones */
  color: #007bff; /* Texto azul */
  border: 1px solid #007bff; /* Borde azul */
  padding: 10px 15px; /* Espaciado interno */
  border-radius: 5px; /* Bordes redondeados */
  font-size: 1rem; /* Tamaño del texto */
  cursor: pointer; /* Cursor cambia al pasar sobre el botón */
  transition: background-color 0.3s, color 0.3s; /* Transición suave */
}

.nav-button:hover {
  background-color: #0056b3; /* Fondo azul más oscuro al pasar */
  color: white; /* Texto blanco al pasar */
}

.logout-button {
  background-color: #dc3545; /* Fondo rojo */
  color: white; /* Texto blanco */
  border: none; /* Sin bordes */
  padding: 10px 15px; /* Espaciado interno */
  border-radius: 5px; /* Bordes redondeados */
  font-size: 1rem; /* Tamaño del texto */
  cursor: pointer; /* Cursor cambia al pasar sobre el botón */
  margin-right: 20px; /* Separación del borde derecho */
  transition: background-color 0.3s; /* Transición suave */
}

.logout-button:hover {
  background-color: #b02a37; /* Fondo rojo más oscuro al pasar */
}
</style>
