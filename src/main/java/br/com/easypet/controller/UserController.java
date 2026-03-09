package br.com.easypet.controller;

import br.com.easypet.dto.request.ChangePasswordRequest;
import br.com.easypet.dto.request.UpdateProfileRequest;
import br.com.easypet.dto.response.UserResponse;
import br.com.easypet.repository.UserRepository;
import br.com.easypet.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints de gerenciamento de perfil")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Buscar perfil do usuário autenticado")
    public ResponseEntity<UserResponse> getProfile(){
        return ResponseEntity.ok(userService.getProfile());
    }

    @PutMapping("/me")
    @Operation(summary = "Atualizar perfil do usuário autenticado")
    public ResponseEntity<UserResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateProfile(request));
    }

    @PatchMapping("/me/password")
    @Operation(summary = "Trocar senha do usuário autenticado")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.noContent().build();
    }
}
