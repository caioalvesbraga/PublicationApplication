package libraryProject.restApi.controllers;

import libraryProject.restApi.dto.PublicationDto;
import libraryProject.restApi.dto.PublicationResponseDto;
import libraryProject.restApi.services.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publications")
public class PublicationController {
    @Autowired
    private PublicationService publicationService;

    @GetMapping
    public ResponseEntity<List<PublicationResponseDto>> getPublication() {
        try {
            return ResponseEntity.ok(publicationService.getPublications());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<PublicationDto> addPublication(@RequestBody PublicationDto publicationDto) {
        try {
            return ResponseEntity.ok(publicationService.addPublication(publicationDto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDto> updatePublication(@PathVariable Long id, @RequestBody PublicationDto publicationDto) {
        try {
            return ResponseEntity.ok(publicationService.updatePublication(id, publicationDto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deletePublication(@PathVariable Long id) {
        try {
            publicationService.deletePublication(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
