package library.entity;

import library.dto.request.RegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    public User(RegisterRequest registerRequest){
        this.name = registerRequest.getName();
        this.surname = registerRequest.getSurname();
        this.email = registerRequest.getEmail();
        this.password = registerRequest.getPassword();
    }
}
