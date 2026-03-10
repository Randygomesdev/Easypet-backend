package br.com.easypet.controller;

import br.com.easypet.dto.request.AppointmentRequest;
import br.com.easypet.dto.response.AppointmentResponse;
import br.com.easypet.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Endpoints de gerenciamento de agendamentos")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @Operation(summary = "Criar agendamento")
    public ResponseEntity<AppointmentResponse> create(
            @Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.create(request));
    }

    @GetMapping
    @Operation(summary = "Listar agendamentos do usuário autenticado")
    public ResponseEntity<List<AppointmentResponse>> findAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar agendamento por ID")
    public ResponseEntity<AppointmentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.findById(id));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancelar agendamento")
    public ResponseEntity<AppointmentResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancel(id));
    }

    @GetMapping("/vet")
    @Operation(summary = "Listar agendamentos do veterinário autenticado")
    public ResponseEntity<List<AppointmentResponse>> findByVet() {
        return ResponseEntity.ok(appointmentService.findByVet());
    }
}