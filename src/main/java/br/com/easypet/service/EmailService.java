package br.com.easypet.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Async
    public void sendVaccineReminder(
            String toEmail,
            String ownerName,
            String petName,
            String vaccineName,
            String laboratory,
            String lote,
            LocalDate nextDoseDate,
            int daysUntilDue) {

        try {
            Context context = new Context();
            context.setVariable("ownerName", ownerName);
            context.setVariable("petName", petName);
            context.setVariable("vaccineName", vaccineName);
            context.setVariable("laboratory", laboratory);
            context.setVariable("lote", lote);
            context.setVariable("nextDoseDate",
                    nextDoseDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            context.setVariable("daysUntilDue", daysUntilDue);

            String html = templateEngine.process("vaccine-reminder", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(buildSubject(petName, daysUntilDue));
            helper.setText(html, true);

            mailSender.send(message);
            log.info("Email enviado para {} — vacina {} do pet {}",
                    toEmail, vaccineName, petName);

        } catch (MessagingException e) {
            log.error("Erro ao enviar email para {}: {}", toEmail, e.getMessage());
        }
    }

    private String buildSubject(String petName, int daysUntilDue) {
        return switch (daysUntilDue) {
            case 0 -> "🚨 [EasyPet] A vacina do " + petName + " vence HOJE!";
            case 10 -> "⏰ [EasyPet] Faltam 10 dias para a vacina do " + petName;
            default -> "📅 [EasyPet] Lembrete: vacina do " + petName + " em 30 dias";
        };
    }
}