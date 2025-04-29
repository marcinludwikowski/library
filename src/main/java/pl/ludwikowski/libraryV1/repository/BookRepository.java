package pl.ludwikowski.libraryV1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ludwikowski.libraryV1.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorLastNameContainingIgnoreCase(String authorLastName);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByCategoryContaining(String category);

}
