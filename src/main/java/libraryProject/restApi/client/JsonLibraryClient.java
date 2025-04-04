package libraryProject.restApi.client;

import libraryProject.restApi.dto.PublicationJsonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "jsonlibrary", url = "publicationsapi-production-e1e0.up.railway.app")
public interface JsonLibraryClient {

    @GetMapping
    List<PublicationJsonDto> getPublications();

    @GetMapping("/autor")
    List<PublicationJsonDto> getPublicationsByAuthor(@RequestParam String author);

    @GetMapping("/isbn")
    List<PublicationJsonDto> getPublicationsByIsbn(@RequestParam Long isbn);

//    @PostMapping
//    PublicationJsonDto addPublication(@RequestBody PublicationJsonDto publicationJsonDto);
//
//    @PutMapping("/update")
//    PublicationJsonDto updatePublication(@RequestParam Long isbn, @RequestBody PublicationJsonDto publicationJsonDto);
}
