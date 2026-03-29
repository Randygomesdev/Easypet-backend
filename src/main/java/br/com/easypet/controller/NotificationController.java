package br.com.easypet.controller;

import br.com.easypet.service.VaccineScheduler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notificações", description = "Endpoints de teste de notificações")
public class NotificationController {

    private final VaccineScheduler vaccineScheduler;

    @PostMapping("/vaccines/check")
    @Operation(summary ="Dispara manualmente a verificação de vacinas")
    public ResponseEntity<String> checkVaccines(){
        vaccineScheduler.checkVaccineReminders();
        return ResponseEntity.ok("Verificação de vacinas disparada com sucesso!");
    }
}
