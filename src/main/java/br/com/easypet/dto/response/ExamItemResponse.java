package br.com.easypet.dto.response;

import br.com.easypet.domain.entity.ExamItem;

public record ExamItemResponse(
    Long id,
    String examName,
    String reason
) {
    public static ExamItemResponse from(ExamItem examItem) {
        return new ExamItemResponse(
            examItem.getId(),
            examItem.getExamName(),
            examItem.getReason()
        );
    }
}
