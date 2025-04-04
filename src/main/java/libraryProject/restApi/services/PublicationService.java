package libraryProject.restApi.services;

import libraryProject.restApi.dto.PublicationDto;
import libraryProject.restApi.dto.PublicationResponseDto;
import libraryProject.restApi.mapper.PublicationMapper;
import libraryProject.restApi.models.PublicationEntity;
import libraryProject.restApi.repositories.PublicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    public PublicationService(PublicationRepository publicationRepository, PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    public List<PublicationResponseDto> getPublications() {
        return publicationMapper.mapPublicationResponseDtoList(publicationRepository.findAll());
    }

    public List<PublicationEntity> getPublicationEntities() {
        return publicationRepository.findAll();
    }

    public PublicationDto addPublication(PublicationDto publicationDto) {
        return publicationMapper.mapPublicationDto(publicationRepository.save(publicationMapper.mapPublicationEntity(publicationDto)));
    }

    public PublicationDto updatePublication(Long id, PublicationDto publicationDto) {
        getPublicationEntities().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(
                        p -> p.setTitle(publicationDto.getTitle()),
                        () -> {throw new RuntimeException();}
                );
        publicationMapper.mapPublicationEntity(publicationDto);
        return publicationDto;
    }

    public void deletePublication(Long id) {
        getPublicationEntities().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(
                        publicationRepository::delete,
                        () -> {throw new RuntimeException();}
                );
    }
}
