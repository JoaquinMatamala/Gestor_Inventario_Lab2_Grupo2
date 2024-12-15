import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class RegisterService {
  async register(name, email, role, phone_number, password) {
    try {
      const response = await axios.post(
        `${API_URL}/auth/register`,
        {
          name,
          email,
          role,
          phone_number,
          password
        },
        {
          headers: {
            "Content-Type": "application/json"
          }
        }
      );
      return response.data;
    } catch (error) {
      console.error("Error al registrar el usuario:", error);
      throw error;
    }
  }

  async completeRegistration(clientId, address, phoneNumber) {
    try {
      const response = await axios.put(
        `${API_URL}/client/complete-registration/${clientId}`,
        {
          address,
          phoneNumber,
        },
        {
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`,
            "Content-Type": "application/json",
          },
        }
      );
      return response.data;
    } catch (error) {
      console.error("Error al completar el registro:", error);
      throw error;
    }
  }
}

class LoginService {
  async login(email, password) {
    const data = { email, password };
  
    console.log("Datos enviados al backend:", JSON.stringify(data, null, 2));
  
    try {
      const response = await axios.post(`${API_URL}/auth/login`, data);
  
      // Extraer token, client_id y role desde la respuesta
      const { token, client_id, role } = response.data;
  
      if (!client_id || !role) {
        throw new Error("El backend no devolvió client_id o role.");
      }
  
      // Guardar token, client_id y role en localStorage
      localStorage.setItem("jwtToken", token);
      localStorage.setItem("clientId", client_id.toString());
      localStorage.setItem("role", role);
  
      console.log("Login exitoso:", { token, client_id, role });
      return { token, client_id, role };
    } catch (error) {
      console.error("Error al iniciar sesión:", error.response?.data || error.message);
      throw error;
    }
  }
  

  async checkToken() {
    try {
      const token = localStorage.getItem("jwtToken");
      const response = await axios.post(
        `${API_URL}/auth/check-token`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return response.data;
    } catch (error) {
      console.error("Error al verificar el token:", error.response?.data || error.message);
      throw error;
    }
  }
}

async function validateSession() {
  const loginService = new LoginService();
  try {
    await loginService.checkToken();
    return true;
  } catch (error) {
    alert("Tu sesión ha expirado. Por favor, inicia sesión nuevamente.");
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("userId");
    window.location.href = "/";
    return false;
  }
}

export const registerService = new RegisterService();
export const loginService = new LoginService();
export { validateSession };
