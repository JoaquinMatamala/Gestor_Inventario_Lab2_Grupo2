<template>
    <div id="main-page">
      <!-- Contenido principal con botones -->
      <main>
        <button @click="handleButton1Click">Iniciar Sesión</button>
        <button @click="handleButton2Click">Registrarse</button>
      </main>
    </div>
  </template>
  
  <script>
  export default {
    name: 'MainPage',
    methods: {
      handleButton1Click() {
        this.$router.push('/login'); // Navega a la ruta /login
      },
      handleButton2Click() {
        this.$router.push('/register'); // Navega a la ruta /register
      }
    }
  }
  </script>
  
  <style scoped>
  /* Estilos globales */
  #main-page {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }
  
  /* Ajuste para el contenido debajo del header */
  #main-page > *:not(header) {
    margin-top: 100px;        /* Desplaza el contenido para evitar que quede oculto debajo del header */
  }
  
  /* Estilos para el contenido principal */
  main {
    display: flex;
    justify-content: center;  /* Centra horizontalmente los botones */
    align-items: center;      /* Centra verticalmente los botones */
    flex-direction: column;   /* Alinea los botones en columna */
    gap: 20px;                /* Espaciado entre botones */
    height: calc(100vh - 100px); /* Altura completa menos el header */
  }
  
  /* Estilos para los botones */
  main button {
    font-size: 1.2rem;       /* Tamaño de texto */
    padding: 10px 20px;      /* Espaciado interno */
    border: none;            /* Sin bordes */
    border-radius: 5px;      /* Bordes redondeados */
    background-color: #007bff; /* Fondo azul */
    color: white;            /* Texto blanco */
    cursor: pointer;         /* Cambia el cursor al pasar por encima */
    transition: background-color 0.3s ease; /* Transición de color */
  }
  
  main button:hover {
    background-color: #0056b3; /* Color al pasar el cursor */
  }
  </style>

  