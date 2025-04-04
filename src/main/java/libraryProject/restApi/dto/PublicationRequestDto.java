package libraryProject.restApi.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicationRequestDto {
    @NotNull(message = "O título deve ser preenchido.")
    @Pattern(regexp = "^(?!\\s*$).+", message = "O título não pode ser vazio nem conter apenas espaços.")
    private String title;

    @NotNull(message = "O nome do deve ser preenchido.")
    @Pattern(regexp = "^(?!\\s*$).+", message = "O nome do autor não pode ser vazio nem conter apenas espaços.")
    private String author;

    @NotNull(message = "O ISBN deve ser preenchido")
    @Min(value = 1000000000000L, message = "O ISBN deve ter exatos 13 dígitos.")
    @Max(value = 9999999999999L, message = "O ISBN deve ter exatos 13 dígitos.")
    private Long isbn;

    @NotNull(message = "A quantidade de páginas deve ser preenchida.")
    @Positive(message = "A quantidade de páginas deve ser um número inteiro maior que zero.")
    private Integer pagesQuantity;

    @NotNull(message = "O ano de lançamento do livro deve ser preenchido.")
    @Positive(message = "O ano de lançamento deve ser um número inteiro maior que zero.")
    private Integer launchYear;
}
