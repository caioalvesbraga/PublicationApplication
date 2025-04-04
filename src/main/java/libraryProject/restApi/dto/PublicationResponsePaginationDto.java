package libraryProject.restApi.dto;

import java.util.List;

public record PublicationResponsePaginationDto (Integer totalPages,
                                                Long totalPublications,
                                                Integer publicationsPerPage,
                                                Integer pageNumber,
                                                List<PublicationResponseDto> publicationResponseDtos) {
}
