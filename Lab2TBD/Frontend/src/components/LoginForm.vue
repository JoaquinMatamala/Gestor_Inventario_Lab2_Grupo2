<template>
  <div class="login-container">
    <h2 class="text-center mb-4">Iniciar Sesión</h2>
    <form @submit.prevent="login" class="login-form">
      <div class="mb-3">
        <label for="email" class="form-label">Correo Electrónico</label>
        <input 
          type="email" 
          class="form-control" 
          id="email" 
          v-model="email" 
          placeholder="Ingresa tu correo electrónico" 
          required
        />
      </div>

      <div class="mb-3">
        <label for="password" class="form-label">Contraseña</label>
        <input 
          type="password" 
          class="form-control" 
          id="password" 
          v-model="password" 
          placeholder="Ingresa tu contraseña" 
          required
        />
      </div>

      <button type="submit" class="btn btn-primary w-100">Iniciar Sesión</button>
    </form>
  </div>
</template>

<script>
import { loginService } from "@/services/client.service";

export default {
  name: "LoginForm",
  data() {
    return {
      email: "",
      password: ""
    };
  },
  methods: {
    async login() {
      try {
        const response = await loginService.login(this.email, this.password);

        console.log("Sesión Iniciada:", response);
        alert("Sesión Iniciada");

        this.$router.push("/products");
      } catch (error) {
        console.error("Error al iniciar sesión:", error.response?.data || error.message);
        alert("Error al iniciar sesión. Por favor, verifica tus credenciales.");
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

h2 {
  color: #333;
}

.login-form .form-label {
  font-weight: 500;
  color: #555;
}

.login-form .form-control:focus {
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.25);
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}

.btn-primary:hover {
  background-color: #0056b3;
  box-shadow: 0 0 10px rgba(0, 123, 255, 0.5);
}
</style>
