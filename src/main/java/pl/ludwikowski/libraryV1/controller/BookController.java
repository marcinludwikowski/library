package pl.ludwikowski.libraryV1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.ludwikowski.libraryV1.model.Book;
import pl.ludwikowski.libraryV1.service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    BookService bookService;

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get all books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get book by id")
    public Optional<Book> findBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }

    @PostMapping@SecurityRequirement(name = "Bearer Authentication")
    @Secured("ADMIN")
    @Operation(summary = "Add new book")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/updateBook/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Secured("ADMIN")
    @Operation(summary = "Update book")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        updatedBook.setId(id);
        Book savedBook = bookService.updateBook(updatedBook);
        return ResponseEntity.ok(savedBook);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Secured("ADMIN")
    @Operation(summary = "Delete book")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }


    @GetMapping("/title/{title}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Search books by title")
    public List<Book> searchBooksByTitle(@RequestParam String title) {
        return bookService.searchBooksByTitle(title);
    }

    @GetMapping("/author/{authorLastName}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Search books by author")
    public List<Book> searchBooksByAuthor(@RequestParam String authorLastName) {
        return bookService.searchBooksByAuthor(authorLastName);
    }

    @GetMapping("/category/{category}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Search books by category")
    public List<Book> searchBooksByCategory(@RequestParam String category) {
        return bookService.searchBooksByCategory(category);
    }


    @GetMapping("/total-size")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get total number of books in the library")
    public ResponseEntity<Long> getTotalLibrarySize() {
        long totalLibrarySize = bookService.getTotalLibrarySize();
        return ResponseEntity.ok(totalLibrarySize);
    }

    @GetMapping("/total-number-of-borrowed-books")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get total number of borrowed books")
    public ResponseEntity<Long> getBorrowedBooksCount() {
        long borrowedBooksCount = bookService.getBorrowedBooksCount();
        return ResponseEntity.ok(borrowedBooksCount);
    }

    @GetMapping("/total-number-of-borrowing")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get total number of borrowing")
    public ResponseEntity<Long> getTotalBorrowingAmount() {
        long totalBorrowingAmount = bookService.getTotalBorrowingAmount();
        return ResponseEntity.ok(totalBorrowingAmount);
    }

    @GetMapping("/most-popular-books")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get most popular books")
    public List<Book> getMostPopularBooks() {
        return bookService.getMostPopularBooks();
    }

    @GetMapping("/{bookId}/available")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Checking if the book is available for borrowing")
    public ResponseEntity<Boolean> isBookAvailable(@PathVariable Long bookId) {
        boolean isAvailable = bookService.isBookAvailable(bookId);
        return ResponseEntity.ok(isAvailable);
    }


    @GetMapping("display-in-shortened-format")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Display books in shortened format")
    public List<String> displayInShortenedFormat() {
        return bookService.displayInShortenedFormat();
    }

}
