<template>
    <div class="ranking-detail-container">
      <h1>Detalle del Usuario: {{ userId }}</h1>
      <table>
        <thead>
          <tr>
            <th>ID Log</th>
            <th>Acción</th>
            <th>Tabla</th>
            <th>Consulta o Acción desde Backend</th>
            <th>Fecha</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="log in userLogs" :key="log.audit_id">
            <td>{{ log.audit_id }}</td>
            <td><strong>{{ log.action_type }}</strong></td>
            <td>{{ log.table_name }}</td>
            <td>{{ log.executed_query }}</td>
            <td>{{ log.action_timestamp }}</td>
          </tr>
        </tbody>
      </table>
      <button @click="goBack">Volver</button>
    </div>
  </template>
  
  <script>
  import AuditService from "@/services/audit.service";
  
  export default {
    name: "RankingDetail",
    data() {
      return {
        userId: null,
        userLogs: [],
      };
    },
    methods: {
      async fetchUserLogs() {
        try {
          console.log(`Cargando logs para el usuario ${this.userId}...`);
          const response = await AuditService.getUserLogs(this.userId);
          this.userLogs = response;
          console.log("Logs cargados:", this.userLogs);
        } catch (error) {
          console.error("Error al cargar los logs del usuario:", error.message);
        }
      },
      goBack() {
        this.$router.push("/ranking-queries");
      },
    },
    created() {
      this.userId = this.$route.params.id;
      console.log("Cargando componente para el usuario con ID:", this.userId);
      this.fetchUserLogs();
    },
  };
  </script>
  
  <style scoped>
  .ranking-detail-container {
    max-width: 70%;
    margin: 20px auto;
    padding: 20px;
    text-align: center;
    background-color: #f9f9f9;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
    background-color: white;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  th,
  td {
    padding: 10px;
    text-align: left;
    border: 1px solid #ddd;
  }
  
  th {
    background-color: #007bff;
    color: white;
    font-weight: bold;
  }
  
  td {
    background-color: #ffffff;
  }
  
  button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 20px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
  }
  
  button:hover {
    background-color: #0056b3;
    transform: translateY(-2px);
    box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15);
  }
  </style>
  