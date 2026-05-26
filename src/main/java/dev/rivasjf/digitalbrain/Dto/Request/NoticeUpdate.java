package dev.rivasjf.digitalbrain.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class NoticeUpdate {
    @NotEmpty(message = "Id is required")
    Long id;
    @NotBlank(message = "Message is required")
    @Size(min = 1, max = 300)
    String message;

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
