package library.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    @ApiModelProperty(notes = "name", required = true, name = "name", value = "John", example = "John")
    private String name;

    @NotBlank
    @ApiModelProperty(notes = "surname", required = true, name = "surname", value = "Doe", example = "Doe")
    private String surname;

    @NotBlank
    @ApiModelProperty(notes = "email", required = true, name = "email", value = "john.doe@email.com", example = "john.doe@email.com")
    private String email;

    @NotNull
    @ApiModelProperty(notes = "password", required = true, name = "password", value = "johnDoePass123", example = "john.johnDoePass123")
    private String password;

}
