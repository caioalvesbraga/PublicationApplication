package libraryProject.restApi.controllers;

import jakarta.validation.Valid;
import libraryProject.restApi.dto.PublicationRequestDto;
import libraryProject.restApi.dto.PublicationResponseDto;
import libraryProject.restApi.dto.PublicationResponsePaginationDto;
import libraryProject.restApi.services.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publications")
public class PublicationController {
    private final PublicationService publicationService;

    @GetMapping
    public ResponseEntity<PublicationResponsePaginationDto> getPublications(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer publicationsPerPage) {
        return ResponseEntity.ok(publicationService.getPublications(title, pageNumber, publicationsPerPage));
    }

    @GetMapping("/id")
    public ResponseEntity<PublicationResponseDto> getPublicationsById(@RequestParam(required = false) Long id) {
        return ResponseEntity.ok(publicationService.getPublicationsById(id));
    }

    @GetMapping("/author")
    public ResponseEntity<PublicationResponsePaginationDto> getPublicationsByAuthor(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer publicationsPerPage) {
        return ResponseEntity.ok(publicationService.getPublicationsByAuthor(author, pageNumber, publicationsPerPage));
    }

    @GetMapping("/isbn")
    public ResponseEntity<PublicationResponsePaginationDto> getPublicationsByIsbn(
            @RequestParam Long isbn,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer publicationsPerPage) {
        return ResponseEntity.ok(publicationService.getPublicationsByIsbn(isbn, pageNumber, publicationsPerPage));
    }

    @PostMapping
    public ResponseEntity<PublicationResponseDto> addPublication(@Valid @RequestBody PublicationRequestDto publicationRequestDto) {
        return new ResponseEntity<>(publicationService.addPublication(publicationRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationResponseDto> updatePublication(@Valid @PathVariable Long id,
                                                                    @Valid @RequestBody PublicationRequestDto publicationRequestDto) {
        return ResponseEntity.ok(publicationService.updatePublication(id, publicationRequestDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublication(@Valid @PathVariable Long id) {
        publicationService.deletePublication(id);
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void importPublications() {
        publicationService.publicationImportAll();
    }

    @PostMapping("/import/author")
    @ResponseStatus(HttpStatus.CREATED)
    public void importPublicationsByAuthor(@RequestParam String author) {
        publicationService.publicationImportByAuthor(author);
    }

    @PostMapping("/import/isbn")
    @ResponseStatus(HttpStatus.CREATED)
    public void importPublicationsByAuthor(@RequestParam Long isbn) {
        publicationService.publicationImportByIsbn(isbn);
    }
}
