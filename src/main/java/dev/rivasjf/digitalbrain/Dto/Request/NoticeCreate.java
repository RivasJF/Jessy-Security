package dev.rivasjf.digitalbrain.Dto.Request;

import dev.rivasjf.digitalbrain.Entities.Notice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NoticeCreate {

    @NotBlank(message = "Message is required")
    @Size(min = 1, max = 300)
    private String message;
    public String getMessage() {
        return message;
    }
}
