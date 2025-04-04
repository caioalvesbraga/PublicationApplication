package libraryProject.restApi.mapper;

import libraryProject.restApi.dto.PublicationDto;
import libraryProject.restApi.dto.PublicationResponseDto;
import libraryProject.restApi.models.PublicationEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicationMapper {
    PublicationDto mapPublicationDto(PublicationEntity publicationEntity);
    List<PublicationDto> mapPublicationDtoList(List<PublicationEntity> publicationEntities);

    PublicationEntity mapPublicationEntity(PublicationDto publicationDto);
    List<PublicationEntity> mapPublicationEntityList(List<PublicationDto> publicationDto);

    List<PublicationResponseDto> mapPublicationResponseDtoList(List<PublicationEntity> publicationEntities);
    PublicationResponseDto mapPublicationResponseDtoList(PublicationEntity publicationEntity);
}
