package com.example.Lab1TBD.controllers;
import com.example.Lab1TBD.persistence.dto.LoginDto;
import com.example.Lab1TBD.persistence.entities.ClientEntity;
import com.example.Lab1TBD.persistence.repositories.ClientRepository;
import com.example.Lab1TBD.persistence.dto.RegisterDto;
import com.example.Lab1TBD.persistence.entities.ClientEntity;
import com.example.Lab1TBD.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.Lab1TBD.config.JwtUtil;
import com.example.Lab1TBD.persistence.dto.LoginDto;
import com.example.Lab1TBD.persistence.dto.RegisterDto;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientService clientService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, ClientRepository clientRepository, PasswordEncoder passwordEncoder, ClientService clientService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        // Imprimir los datos recibidos en el backend
        System.out.println("Datos recibidos en el backend: " + loginDto);

        try {
            // Autenticar al usuario con los datos proporcionados
            UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()
            );
            authenticationManager.authenticate(loginToken);

            // Si la autenticación es exitosa, generar JWT
            String jwt = jwtUtil.create(loginDto.getEmail());

            // Obtener el usuario autenticado de la base de datos
            ClientEntity client = clientRepository.findClientByEmail(loginDto.getEmail());
            if (client == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuario no encontrado.");
            }

            // Registrar el login en la tabla audit_log
            clientService.logUserLogin(client.getClient_id());

            // Construir la respuesta con el token y la clientId
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("client_id", client.getClient_id());

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas.");
        }
    }




    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        // Verificar si el usuario ya existe
        ClientEntity foundClient = clientService.getClientByEmail(registerDto.getEmail());
        if (foundClient != null) { // Usuario ya existe
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe.");
        }

        try {
            // Crear nuevo usuario
            ClientEntity newClient = new ClientEntity(
                    null, // ID autogenerado
                    registerDto.getName(), // Nombre del usuario
                    registerDto.getAddress(), // Dirección del usuario
                    registerDto.getEmail(), // Email
                    passwordEncoder.encode(registerDto.getPassword()), // Encriptar contraseña
                    registerDto.getPhone_number() // Número de teléfono
            );
            clientRepository.saveClient(newClient);

            // Obtener el usuario recién creado para registrar la acción
            ClientEntity createdClient = clientService.getClientByEmail(registerDto.getEmail());
            clientService.logUserRegistration(createdClient.getClient_id());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuario registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar usuario.");
        }
    }
    @PostMapping("/check-token")
    public ResponseEntity<?> checkToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token inválido.");
            }

            token = token.replace("Bearer ", ""); // Eliminar prefijo "Bearer"
            boolean isValid = jwtUtil.isValid(token); // Usar isValid en lugar de validateToken

            if (!isValid) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token inválido.");
            }

            return ResponseEntity.ok(Map.of("message", "Token válido"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al validar el token: " + e.getMessage());
        }
    }
}
