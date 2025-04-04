package libraryProject.restApi.dto;

public record PublicationJsonDto (String titulo, String autor, Long isbn, Integer paginas, Integer ano) {
}
