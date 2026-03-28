package br.com.easypet.controller;

import br.com.easypet.dto.request.LoginRequest;
import br.com.easypet.dto.request.RegisterRequest;
import br.com.easypet.dto.request.RegisterVetRequest;
import br.com.easypet.dto.response.AuthResponse;
import br.com.easypet.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de registro e login")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/admin")
    @Operation(summary = "Registrar administrador (remover em produção)")
    public ResponseEntity<AuthResponse> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.registerAdmin(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.email())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.email())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/register/vet")
    @Operation(summary = "Registrar novo veterinário")
    public ResponseEntity<AuthResponse> registerVet(@Valid @RequestBody RegisterVetRequest request) {
        AuthResponse response = authService.registerVet(request.credentials(),request.professional());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.email())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar Login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}