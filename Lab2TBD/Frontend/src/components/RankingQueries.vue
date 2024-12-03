<template>
  <div class="ranking-container">
    <h1>Ranking de Usuarios</h1>
    <table>
      <thead>
        <tr>
          <th>ID Usuario</th>
          <th>INSERT</th>
          <th>UPDATE</th>
          <th>DELETE</th>
          <th>Acción</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in ranking" :key="user.user_id">
          <td>{{ user.user_id }}</td>
          <td>{{ user.insert_count }}</td>
          <td>{{ user.update_count }}</td>
          <td>{{ user.delete_count }}</td>
          <td>
            <button @click="viewDetails(user.user_id)">Ver Detalle</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import AuditService from "@/services/audit.service";

export default {
  name: "RankingQueries",
  data() {
    return {
      ranking: [],
    };
  },
  methods: {
    async fetchRanking() {
      try {
        console.log("Cargando ranking...");
        this.ranking = await AuditService.getUserActionRanking();
      } catch (error) {
        console.error("Error al cargar el ranking:", error.message);
      }
    },
    viewDetails(userId) {
      console.log(`Redireccionando al detalle del usuario con ID ${userId}...`);
      this.$router.push(`/ranking/detail/user/${userId}`);
    },
  },
  created() {
    this.fetchRanking();
  },
};
</script>

<style scoped>
.ranking-container {
  max-width: 70%;
  margin: 20px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
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
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

button:hover {
  background-color: #0056b3;
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15);
}
</style>
