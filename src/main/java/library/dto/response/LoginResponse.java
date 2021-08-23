package library.dto.response;

import library.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: IMPLEMENT THIS PROPER
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;

    private UserDTO user;

}
