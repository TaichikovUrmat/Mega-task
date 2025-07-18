package urmat.dev.megatask.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskResponse(
        Long taskId,
        String title,
        String description,
        boolean completed,
        LocalDate createdAt
) {
}
