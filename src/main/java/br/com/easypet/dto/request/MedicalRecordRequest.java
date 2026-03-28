package br.com.easypet.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;  

public record MedicalRecordRequest(

    @NotNull(message = "O diagnóstico é obrigatório")
    String diagnosis,

    String generalInstructions,

    List<PrescriptionItemRequest> prescriptions,

    List<ExamItemRequest> exams
) {

}
