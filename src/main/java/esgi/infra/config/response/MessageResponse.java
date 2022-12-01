package esgi.infra.config.response;

import lombok.*;

@Data
@AllArgsConstructor
public class MessageResponse {

    private Boolean success;
    private String message;

}
