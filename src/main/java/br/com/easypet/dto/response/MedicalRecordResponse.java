package br.com.easypet.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import br.com.easypet.domain.entity.MedicalRecord;

public record MedicalRecordResponse(
        long id,
        long appointmentId,
        String diagnosis,
        String generalInstructions,
        List<PrescriptionItemResponse> prescriptions,
        List<ExamItemResponse> exams,
        LocalDateTime createdAt
) {
    public static MedicalRecordResponse from(MedicalRecord medicalRecord) {
        return new MedicalRecordResponse(
                medicalRecord.getId(),
                medicalRecord.getAppointment().getId(),
                medicalRecord.getDiagnosis(),
                medicalRecord.getGeneralInstructions(),
                medicalRecord.getPrescriptions() != null ?
                        medicalRecord.getPrescriptions().stream().map(PrescriptionItemResponse::from).toList() : null,
                medicalRecord.getExams() != null ?
                        medicalRecord.getExams().stream().map(ExamItemResponse::from).toList() : null,
                medicalRecord.getCreatedAt()
        );
    }
}
