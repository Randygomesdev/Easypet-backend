package br.com.easypet.service;

import br.com.easypet.domain.entity.Vaccine;
import br.com.easypet.repository.VaccineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class VaccineScheduler {

    private final VaccineRepository vaccineRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 8 * * *")
    public void checkVaccineReminders() {
        log.info("Iniciando verificação de vacinas — {}", LocalDate.now());

        checkVaccinesForDay(30);
        checkVaccinesForDay(10);
        checkVaccinesForDay(0);

        log.info("Verificação de vacinas concluída");
    }

    private void checkVaccinesForDay(int daysUntilDue) {
        LocalDate targetDate = LocalDate.now().plusDays(daysUntilDue);
        List<Vaccine> vaccines = vaccineRepository.findByNextDoseDateBetween(
                targetDate, targetDate
        );

        log.info("Vacinas vencendo em {} dias: {}", daysUntilDue, vaccines.size());

        for (Vaccine vaccine : vaccines) {
            String ownerEmail = vaccine.getPet().getOwner().getEmail();
            String ownerName = vaccine.getPet().getOwner().getName();

            if (ownerEmail == null || ownerEmail.isBlank()) {
                log.warn("Pet {} sem email válido do tutor — pulando", vaccine.getPet().getName());
                continue;
            }

            emailService.sendVaccineReminder(
                    ownerEmail,
                    ownerName,
                    vaccine.getPet().getName(),
                    vaccine.getName(),
                    vaccine.getLaboratory(),
                    vaccine.getLote(),
                    vaccine.getNextDoseDate(),
                    daysUntilDue
            );
        }
    }
}