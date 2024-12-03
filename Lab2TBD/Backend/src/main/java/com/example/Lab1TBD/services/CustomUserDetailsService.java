package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.ClientEntity;
import com.example.Lab1TBD.persistence.repositories.ClientRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public CustomUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar el cliente en la base de datos por su email
        ClientEntity client = clientRepository.findClientByEmail(email);

        if (client == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

        // Crear el UserDetails con los datos del cliente
        return User.builder()
                .username(client.getEmail()) // Usamos el email como nombre de usuario
                .password(client.getPassword()) // La contraseña debe estar cifrada
                .roles("USER") // Cambiar si tienes roles adicionales
                .build();
    }
}
