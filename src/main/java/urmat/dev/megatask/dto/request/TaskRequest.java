package urmat.dev.megatask.dto.request;

import lombok.*;

@Setter
@Getter
@Builder
public class TaskRequest {
    private String title;
    private String description;
    private boolean completed;

}
