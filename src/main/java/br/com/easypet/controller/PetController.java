package br.com.easypet.controller;

import br.com.easypet.dto.request.PetRequest;
import br.com.easypet.dto.response.PetResponse;
import br.com.easypet.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@Tag(name = "Pets", description = "Endpoints de gerenciamento de pets")
public class PetController {

    private final PetService petService;

    @PostMapping
    @Operation(summary = "Cadastrar novo pet")
    public ResponseEntity<PetResponse> create(@Valid @RequestBody PetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.create(request));
    }

    @GetMapping
    @Operation(summary = "Listar todos os pets do usuário autenticado")
    public ResponseEntity<List<PetResponse>> findAll() {
        return ResponseEntity.ok(petService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pet por ID")
    public ResponseEntity<PetResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pet")
    public ResponseEntity<PetResponse> update(@PathVariable Long id, @Valid @RequestBody PetRequest request) {
        return ResponseEntity.ok(petService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pet")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
