<template>
  <div class="register-container">
    <h2 class="text-center mb-4">Registro</h2>
    <form @submit.prevent="register" class="register-form">
      <div class="mb-3">
        <label for="name" class="form-label">Nombre</label>
        <input 
          type="text" 
          class="form-control" 
          id="name" 
          v-model="name" 
          placeholder="Ingresa tu nombre" 
          required
        >
      </div>
      
      <div class="mb-3">
        <label for="email" class="form-label">Correo Electrónico</label>
        <input 
          type="email" 
          class="form-control" 
          id="email" 
          v-model="email" 
          placeholder="Ingresa tu correo electrónico" 
          required
        >
      </div>

      <div class="mb-3">
        <label for="phone_number" class="form-label">Número de teléfono</label>
        <input 
          type="tel" 
          class="form-control" 
          id="phone_number" 
          v-model="phone_number" 
          placeholder="Ingresa tu número telefónico" 
          required
        >
      </div>
      
      <div class="mb-3">
        <label for="password" class="form-label">Contraseña</label>
        <input 
          type="password" 
          class="form-control" 
          id="password" 
          v-model="password" 
          placeholder="Mínimo 6 caracteres" 
          required
        >
      </div>
      
      <div class="mb-3">
        <label for="confirmPassword" class="form-label">Confirmar Contraseña</label>
        <input 
          type="password" 
          class="form-control" 
          id="confirmPassword" 
          v-model="confirmPassword" 
          placeholder="Repite tu contraseña" 
          required
        >
      </div>

      <div class="form-check mb-3">
        <input 
          type="checkbox" 
          class="form-check-input" 
          id="isRepartidor" 
          v-model="isRepartidor"
        >
        <label class="form-check-label" for="isRepartidor">¿Quieres registrarte como repartidor?</label>
      </div>
      
      <button type="submit" class="btn btn-primary w-100">Registrarse</button>
    </form>
  </div>
</template>

<script>
import { registerService } from '@/services/client.service';

export default {
  name: 'RegisterForm',
  data() {
    return {
      name: '',
      email: '',
      phone_number: '',
      password: '',
      confirmPassword: '',
      isRepartidor: false, // Estado de la casilla de "Repartidor"
    };
  },
  methods: {
    async register() {
      // Verificar si las contraseñas coinciden
      if (this.password !== this.confirmPassword) {
        alert('Las contraseñas no coinciden');
        return;
      }

      try {
        // Determinar el rol según la casilla de "Repartidor"
        const role = this.isRepartidor ? "REPARTIDOR" : "CLIENTE";

        // Llamada al servicio de registro
        const response = await registerService.register(
          this.name,
          this.email,
          role,
          this.phone_number,
          this.password
        );
        console.log('Usuario registrado:', response);
        alert('Usuario registrado con éxito. Inicia sesión para continuar.');

        // Redirigir al usuario a la página principal
        this.$router.push('/login');
      } catch (error) {
        console.error('Error al registrar el usuario:', error);
        alert('Error al registrar el usuario');
      }
    }
  }
};
</script>

<style scoped>
.register-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
}

h2 {
  color: #333;
}

.register-form .form-label {
  font-weight: 500;
  color: #555;
}

.register-form .form-control {
  border-radius: 6px;
}

.register-form .form-control:focus {
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
w-100