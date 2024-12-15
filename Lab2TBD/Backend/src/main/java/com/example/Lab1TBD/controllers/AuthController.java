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
        try {
            // Buscar usuario por email
            ClientEntity user = clientService.getClientByEmail(loginDto.getEmail());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
            }

            // Validar contraseña
            if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta.");
            }

            // Generar token JWT con el rol del usuario
            String token = jwtUtil.create(user.getEmail(), user.getRole());

            // Devolver token, rol y client_id
            return ResponseEntity.ok().body(
                    Map.of(
                            "token", token,
                            "role", user.getRole(),
                            "client_id", user.getClient_id() // Agregar client_id aquí
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el login.");
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
                    registerDto.getEmail(), // Email
                    passwordEncoder.encode(registerDto.getPassword()), // Encriptar contraseña
                    registerDto.getPhone_number(),// Dirección del usuario// Número de teléfono
                    null, // Dirección del usuario, se completará después
                    registerDto.getRole() // Dirección del usuario
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
