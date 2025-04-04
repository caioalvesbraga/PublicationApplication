package libraryProject.restApi.mapper;

import libraryProject.restApi.dto.PublicationJsonDto;
import libraryProject.restApi.dto.PublicationRequestDto;
import libraryProject.restApi.dto.PublicationResponseDto;
import libraryProject.restApi.models.PublicationEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublicationMapper {
    List<PublicationRequestDto> mapPublicationRequestDtoList(List<PublicationEntity> publicationEntities);
    PublicationRequestDto mapPublicationRequestDto(PublicationEntity publicationEntity);

    List<PublicationEntity> mapPublicationEntityList(List<PublicationRequestDto> publicationRequestDto);
    PublicationEntity mapPublicationEntity(PublicationRequestDto publicationRequestDto);

    List<PublicationResponseDto> mapPublicationResponseDtoList(List<PublicationEntity> publicationEntities);
    PublicationResponseDto mapPublicationResponseDto(PublicationEntity publicationEntity);

    List<PublicationEntity> mapPublicationEntityJsonList(List<PublicationJsonDto> publicationJsonDtos);
    @Mapping(target = "title", source = "titulo")
    @Mapping(target = "author", source = "autor")
    @Mapping(target = "pagesQuantity", source = "paginas")
    @Mapping(target = "launchYear", source = "ano")
    PublicationEntity mapPublicationEntityJson(PublicationJsonDto publicationJsonDto);

    List<PublicationJsonDto> mapPublicationJsonList(List<PublicationResponseDto> publicationResponseDtoList);
    @Mapping(target = "titulo", source = "title")
    @Mapping(target = "autor", source = "author")
    @Mapping(target = "paginas", source = "pagesQuantity")
    @Mapping(target = "ano", source = "launchYear")
    PublicationJsonDto mapPublicationJson(PublicationResponseDto publicationResponseDto);
}
