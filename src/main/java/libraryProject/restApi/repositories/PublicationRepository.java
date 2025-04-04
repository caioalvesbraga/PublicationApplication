package libraryProject.restApi.repositories;

import libraryProject.restApi.models.PublicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<PublicationEntity, Long> {
    @Query("SELECT p FROM PublicationEntity p WHERE :title IS NULL OR p.title LIKE %:title%")
    Page<PublicationEntity> searchByTitle(String title, Pageable pageable);

    @Query("SELECT p FROM PublicationEntity p WHERE p.id = :id")
    PublicationEntity searchById(Long id);

    @Query("SELECT p FROM PublicationEntity p WHERE p.isbn = :isbn")
    Page<PublicationEntity> searchByIsbn(Long isbn, Pageable pageable);

    @Query("SELECT p FROM PublicationEntity p WHERE p.author LIKE %:author%")
    Page<PublicationEntity> searchByAuthor(String author, Pageable pageable);

    @Query("SELECT p FROM PublicationEntity p WHERE p.isbn = :isbn")
    List<PublicationEntity> searchByIsbn(Long isbn);
}
