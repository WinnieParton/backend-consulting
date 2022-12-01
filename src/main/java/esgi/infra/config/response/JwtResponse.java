
package esgi.infra.config.response;

import java.util.List;
import lombok.Data;


@Data
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    // public String getAccessToken() {
    //     return token;
    // }

    // public void setAccessToken(String accessToken) {
    //     this.token = accessToken;
    // }

    // public String getTokenType() {
    //     return type;
    // }

    // public void setTokenType(String tokenType) {
    //     this.type = tokenType;
    // }

}
