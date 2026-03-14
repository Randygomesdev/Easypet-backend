package br.com.easypet.controller;

import br.com.easypet.dto.request.VetRequest;
import br.com.easypet.dto.response.VetResponse;
import br.com.easypet.repository.VetRepository;
import br.com.easypet.service.VetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vets")
@RequiredArgsConstructor
@Tag(name = "Veterinários", description = "Endpoints de gerenciamento de veterinários")
public class VetController {

    private final VetService vetService;

    @GetMapping
    @Operation(summary = "Listar todos os veterinário ativos")
    public ResponseEntity<Page<VetResponse>> findAll(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(vetService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veterinário pelo ID")
    public ResponseEntity<VetResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vetService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veterinário")
    public ResponseEntity<VetResponse> update(@PathVariable Long id, @Valid @RequestBody VetRequest request) {
        return ResponseEntity.ok(vetService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar veterinário")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        vetService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
