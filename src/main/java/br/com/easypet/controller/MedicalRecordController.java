package br.com.easypet.controller;

import br.com.easypet.dto.request.MedicalRecordRequest;
import br.com.easypet.dto.response.MedicalRecordResponse;
import br.com.easypet.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vets/appointments")
@Tag(name = "Prontuário Médicos", description = "Endpoints para registro de prontuários após as consultas")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping("/{appointmentId}/records")
    @Operation(summary = "Criar prontuário médico, receituário e exames")
    public ResponseEntity<MedicalRecordResponse> create (@PathVariable long appointmentId, @Valid @RequestBody MedicalRecordRequest request) {
        MedicalRecordResponse response = medicalRecordService.create(appointmentId, request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
