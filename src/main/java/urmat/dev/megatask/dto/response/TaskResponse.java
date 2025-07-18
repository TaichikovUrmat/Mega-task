package urmat.dev.megatask.dto.response;

import lombok.Builder;

@Builder
public record TaskResponse(
        String title,
        String description,
        boolean completed
) {
}
