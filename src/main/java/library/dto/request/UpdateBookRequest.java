package library.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateBookRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String genre;
}
