package br.com.easypet.controller;

import br.com.easypet.domain.entity.Vaccine;
import br.com.easypet.dto.request.VaccineRequest;
import br.com.easypet.dto.response.VaccineResponse;
import br.com.easypet.service.VaccineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/vaccines")
@RequiredArgsConstructor
@Tag(name = "Vacinas", description = "Endpoints da carteira de vacinas")
public class VaccineController {

    private final VaccineService vaccineService;

    @PostMapping
    @Operation(summary = "Registrar vacina")
    public ResponseEntity<VaccineResponse> create(@Valid @RequestBody VaccineRequest request) {
        VaccineResponse response = vaccineService.create(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar vacinas de um pet")
    public ResponseEntity<Page<VaccineResponse>> findAll(@RequestParam Long petId, @PageableDefault(size = 10, sort = "doseDate")  Pageable pageable) {
        return ResponseEntity.ok(vaccineService.findAll(petId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca vacina por ID")
    public ResponseEntity<VaccineResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vaccineService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vacina")
    public ResponseEntity<VaccineResponse> update(@PathVariable Long id, @Valid @RequestBody VaccineRequest request) {
        return  ResponseEntity.ok(vaccineService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar vacina")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vaccineService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
