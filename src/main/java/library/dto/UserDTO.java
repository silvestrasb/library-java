package library.dto;

import library.entity.Role;
import library.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private Set<Role> roles;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
