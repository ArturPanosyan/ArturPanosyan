package am.developers.auth.service.data;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;
}
