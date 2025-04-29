package pl.ludwikowski.libraryV1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ludwikowski.libraryV1.model.Book;
import pl.ludwikowski.libraryV1.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookService {

    BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll().stream().filter(book -> !book.isDeleted()).collect(Collectors.toList());
    }

    public void deleteBook(Long id) {
        Optional<Book> optionalBook = findBookById(id);

        Book book;
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
            book.setDeleted(true);
            bookRepository.save(book);
        }
    }

    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> searchBooksByAuthor(String authorLastName) {
        return bookRepository.findByAuthorLastNameContainingIgnoreCase(authorLastName).stream().filter(book -> !book.isDeleted()).collect(Collectors.toList());
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream().filter(book -> !book.isDeleted()).collect(Collectors.toList());
    }

    public List<Book> searchBooksByCategory(String category) {
        return bookRepository.findByCategoryContaining(category).stream().filter(book -> !book.isDeleted()).collect(Collectors.toList());
    }

    public long getTotalLibrarySize() {
        return bookRepository.count();
    }

    public long getBorrowedBooksCount() {
        return bookRepository.findAll().stream().filter(book -> book.getIsBorrowed()).count();
    }

    public long getTotalBorrowingAmount() {
        return bookRepository.findAll().stream().mapToInt(book -> book.getBorrowCount()).sum();
    }

    public List<Book> getMostPopularBooks() {
        return bookRepository.findAll().stream().filter(book -> book.getBorrowCount() > 0)
                .sorted((k1, k2) ->Integer.compare(k2.getBorrowCount(), k1.getBorrowCount()))
                .limit(5)
                .toList();
    }

    public boolean isBookAvailable(Long bookId) {
        return !bookRepository.findById(bookId).get().getIsBorrowed();
    }

    private String getInitials(String firstNames) {
        StringBuilder initials = new StringBuilder();
        String[] firstNamesArray = firstNames.split(" ");
        for (String name: firstNamesArray) {
            initials.append(name.charAt(0)).append(".");
        }
        return initials.toString();
    }

    public List<String> displayInShortenedFormat() {
        return bookRepository.findAll().stream()
                .map(book -> String.format("%d, %s, %s, %s, %s",
                        book.getId(),
                        getInitials(book.getAuthorFirstNames()),
                        book.getAuthorLastName(),
                        book.getTitle(),
                        book.getIsBorrowed() ? "yes" : "no"))
                .toList();
    }

}
