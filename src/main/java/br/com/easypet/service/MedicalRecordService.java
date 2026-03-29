package br.com.easypet.service;

import br.com.easypet.domain.entity.*;
import br.com.easypet.domain.enums.AppointmentType;
import br.com.easypet.domain.enums.Role;
import br.com.easypet.dto.request.ExamItemRequest;
import br.com.easypet.dto.request.MedicalRecordRequest;
import br.com.easypet.dto.request.PrescriptionItemRequest;
import br.com.easypet.dto.response.MedicalRecordResponse;
import br.com.easypet.exception.ResourceNotFoundException;
import br.com.easypet.repository.AppointmentRepository;
import br.com.easypet.repository.MedicalRecordRepository;
import br.com.easypet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public MedicalRecordResponse create(Long appointmentId, MedicalRecordRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado"));

        if (appointment.getAppointmentType() != AppointmentType.VET_CONSULTATION) {
            throw new IllegalArgumentException("O prontuário Médico só pode ser preenchido para consultas veterinárias");
        }

        User loggedUser = getAuthenticatedUser();

        if (!appointment.getVet().getUser().getId().equals(loggedUser.getId()) && loggedUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Você só pode criar prontuários para os pets que você mesmo atendeu");
        }

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .appointment(appointment)
                .diagnosis(request.diagnosis())
                .generalInstructions(request.generalInstructions())
                .build();

        if (request.prescriptions() != null) {
            for (PrescriptionItemRequest pReq  : request.prescriptions()) {
                PrescriptionItem pItem = PrescriptionItem.builder()
                        .medicalRecord(medicalRecord)
                        .medicineName(pReq.medicineName())
                        .dosage(pReq.dosage())
                        .duration(pReq.duration())
                        .build();
                medicalRecord.getPrescriptions().add(pItem);
            }
        }

        if (request.exams() != null) {
            for (ExamItemRequest eReq : request.exams()) {
                ExamItem eItem = ExamItem.builder()
                        .medicalRecord(medicalRecord)
                        .examName(eReq.examName())
                        .reason(eReq.reason())
                        .build();
                medicalRecord.getExams().add(eItem);
            }
        }

        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);

        return MedicalRecordResponse.from(savedMedicalRecord);
    }

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
