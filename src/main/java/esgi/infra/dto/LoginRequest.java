
package esgi.infra.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;


@Data
public class LoginRequest {

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotBlank
    private String password;

}
