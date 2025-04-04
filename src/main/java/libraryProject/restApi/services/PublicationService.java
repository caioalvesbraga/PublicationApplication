package libraryProject.restApi.services;

import libraryProject.restApi.client.JsonLibraryClient;
import libraryProject.restApi.exceptions.PaginationException;
import libraryProject.restApi.exceptions.PublicationAlreadyRegisteredException;
import libraryProject.restApi.exceptions.ResourceNotFoundException;
import libraryProject.restApi.dto.PublicationRequestDto;
import libraryProject.restApi.dto.PublicationResponseDto;
import libraryProject.restApi.dto.PublicationResponsePaginationDto;
import libraryProject.restApi.mapper.PublicationMapper;
import libraryProject.restApi.models.PublicationEntity;
import libraryProject.restApi.repositories.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;
    private final JsonLibraryClient jsonLibraryClient;

    @Cacheable("publications")
    public PublicationResponsePaginationDto getPublications(String title, Integer pageNumber, Integer publicationsPerPage) {
        if (pageNumber < 0 || publicationsPerPage < 5) {
            throw new PaginationException();
        }

        Pageable pageable = PageRequest.of(pageNumber, publicationsPerPage, Sort.by("title"));
        Page<PublicationEntity> page = publicationRepository.searchByTitle(title, pageable);

        var publicationResponseDtos = publicationMapper.mapPublicationResponseDtoList(page.getContent());

        return new PublicationResponsePaginationDto(page.getTotalPages(),
                                                    page.getTotalElements(),
                                                    page.getSize(),
                                                    page.getNumber(),
                                                    publicationResponseDtos);
    }

    @Cacheable("publications")
    public PublicationResponseDto getPublicationsById(Long id) {
        var publicationEntity = publicationRepository.searchById(id);
        if (publicationEntity == null) {
            throw new ResourceNotFoundException();
        }
        return publicationMapper.mapPublicationResponseDto(publicationEntity);
    }

    @Cacheable("publications")
    public PublicationResponsePaginationDto getPublicationsByAuthor(String author, Integer pageNumber, Integer publicationsPerPage) {
        if (pageNumber < 0 || publicationsPerPage < 5) {
            throw new PaginationException();
        }

        Pageable pageable = PageRequest.of(pageNumber, publicationsPerPage, Sort.by("author"));
        Page<PublicationEntity> page = publicationRepository.searchByAuthor(author, pageable);

        var publicationResponseDtos = publicationMapper.mapPublicationResponseDtoList(page.getContent());

        if (publicationResponseDtos.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return new PublicationResponsePaginationDto(page.getTotalPages(),
                                                    page.getTotalElements(),
                                                    page.getSize(),
                                                    page.getNumber(),
                                                    publicationResponseDtos);
    }

    @Cacheable("publications")
    public PublicationResponsePaginationDto getPublicationsByIsbn(@RequestParam Long isbn, Integer pageNumber, Integer publicationsPerPage) {
        if (pageNumber < 0 || publicationsPerPage < 5) {
            throw new PaginationException();
        }

        Pageable pageable = PageRequest.of(pageNumber, publicationsPerPage, Sort.by("author"));
        Page<PublicationEntity> page = publicationRepository.searchByIsbn(isbn, pageable);

        var publicationResponseDtos = publicationMapper.mapPublicationResponseDtoList(page.getContent());

        if (publicationResponseDtos.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return new PublicationResponsePaginationDto(page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.getNumber(),
                publicationResponseDtos);
    }

    @CacheEvict(value = "publications", allEntries = true)
    public PublicationResponseDto addPublication(PublicationRequestDto publicationRequestDto) {
        if (publicationRepository
                .findAll()
                .stream()
                .anyMatch(e -> e.getIsbn().equals(publicationRequestDto.getIsbn()))) {
            throw new PublicationAlreadyRegisteredException();
        }
        var publicationEntity = publicationMapper.mapPublicationEntity(publicationRequestDto);
        var savedPublicationEntity = publicationRepository.save(publicationEntity);
        return publicationMapper.mapPublicationResponseDto(savedPublicationEntity);
    }

    @CacheEvict(value = "publications", allEntries = true)
    public PublicationResponseDto updatePublication(Long id, PublicationRequestDto publicationRequestDto) {
        var publicationEntity = publicationRepository.findAll()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
        publicationEntity.setTitle(publicationRequestDto.getTitle());
        publicationEntity.setAuthor(publicationRequestDto.getAuthor());
        publicationEntity.setIsbn(publicationRequestDto.getIsbn());
        publicationEntity.setPagesQuantity(publicationRequestDto.getPagesQuantity());
        publicationEntity.setLaunchYear(publicationRequestDto.getLaunchYear());
        publicationRepository.save(publicationEntity);
        return publicationMapper.mapPublicationResponseDto(publicationEntity);
    }

    @CacheEvict(value = "publications", allEntries = true)
    public void deletePublication(Long id) {
        publicationRepository.findAll()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(publicationRepository::delete, () -> {throw new ResourceNotFoundException();});
    }

    @CacheEvict(value = "publications", allEntries = true)
    public void publicationImportAll() {
        var publicationJsonDtos = jsonLibraryClient.getPublications();
        var publicationEntities = publicationMapper.mapPublicationEntityJsonList(publicationJsonDtos);
        publicationEntities.forEach(p -> {
            if (publicationRepository.searchByIsbn(p.getIsbn()).isEmpty()) {
                publicationRepository.save(p);
            }
        });
    }

    @CacheEvict(value = "publications", allEntries = true)
    public void publicationImportByAuthor(String author) {
        var publicationJsonDtos = jsonLibraryClient.getPublicationsByAuthor(author);
        var publicationEntities = publicationMapper.mapPublicationEntityJsonList(publicationJsonDtos);
        publicationEntities.forEach(p -> {
            if (publicationRepository.searchByIsbn(p.getIsbn()).isEmpty()) {
                publicationRepository.save(p);
            }
        });
    }

    @CacheEvict(value = "publications", allEntries = true)
    public void publicationImportByIsbn(Long isbn) {
        var publicationJsonDtos = jsonLibraryClient.getPublicationsByIsbn(isbn);
        var publicationEntities = publicationMapper.mapPublicationEntityJsonList(publicationJsonDtos);
        publicationEntities.forEach(p -> {
            if (publicationRepository.searchByIsbn(p.getIsbn()).isEmpty()) {
                publicationRepository.save(p);
            }
        });
    }

//    @CacheEvict(value = "publications", allEntries = true)
//    public void deleteIsbnDuplicates() {
//        var publicationEntities = publicationRepository.findAll();
//        publicationEntities.forEach(p -> {
//            var publicationEntityDuplicates = publicationEntities
//                    .stream()
//                    .filter(e -> e.getIsbn().equals(p.getIsbn()))
//                    .toList();
//            if (publicationEntityDuplicates.size() > 1) {
//                publicationRepository.delete(p);}
//        });
//    }
}
