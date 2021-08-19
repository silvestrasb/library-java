package library.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateBookRequest {
    @NotBlank
    @ApiModelProperty(notes = "Book title", required = true, name = "title", value = "Metai", example = "Metai")
    private String title;

    @NotBlank
    @ApiModelProperty(notes = "Book author", required = true, name = "author", value = "Vincas Mykolaitis-Putinas", example = "Vincas Mykolaitis-Putinas")
    private String author;

    @NotBlank
    @ApiModelProperty(notes = "Book genre", required = true, name = "genre", value = "Psychological Novel", example = "Psychological Novel")
    private String genre;
}
