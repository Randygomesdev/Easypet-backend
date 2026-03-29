package br.com.easypet.dto.response;

import br.com.easypet.domain.entity.PrescriptionItem;

public record PrescriptionItemResponse(
    Long id,
    String medicineName,
    String dosage,
    String duration
) {
    public static PrescriptionItemResponse from(PrescriptionItem prescriptionItem) {
        return new PrescriptionItemResponse(
            prescriptionItem.getId(),
            prescriptionItem.getMedicineName(),
            prescriptionItem.getDosage(),
            prescriptionItem.getDuration()
        );
    }
}
