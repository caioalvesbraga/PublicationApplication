package libraryProject.restApi.dto;

public record PublicationResponseDto (Long id,
                                      String title,
                                      String author,
                                      Long isbn,
                                      Integer pagesQuantity,
                                      Integer launchYear) {}
