package br.com.easypet.controller;

import br.com.easypet.dto.request.VaccineApplicationRequest;
import br.com.easypet.dto.response.VaccineApplicationResponse;
import br.com.easypet.service.VaccineApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vets/vaccinations")
@Tag(name = "Aplicação de Vacinas", description = "Endpoints de aplicação de vacinas")
public class VaccineApplicationController {

    private final VaccineApplicationService vaccineApplicationService;

    @PostMapping
    @Operation(summary = "Registrar vacina")
    public ResponseEntity<VaccineApplicationResponse> create(@Valid @RequestBody VaccineApplicationRequest request) {
        VaccineApplicationResponse response = vaccineApplicationService.create(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
