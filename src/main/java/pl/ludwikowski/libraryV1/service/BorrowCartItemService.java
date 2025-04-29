package pl.ludwikowski.libraryV1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ludwikowski.libraryV1.model.Book;
import pl.ludwikowski.libraryV1.model.BorrowCart;
import pl.ludwikowski.libraryV1.model.BorrowCartItem;
import pl.ludwikowski.libraryV1.repository.BookRepository;
import pl.ludwikowski.libraryV1.repository.BorrowCartItemRepository;
import pl.ludwikowski.libraryV1.repository.BorrowCartRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BorrowCartItemService {

    BorrowCartItemRepository borrowCartItemRepository;

    BorrowCartRepository borrowCartRepository;

    BookService bookService;

    BookRepository bookRepository;

    public BorrowCartItem createBorrowCartItem(Book book) {
        return borrowCartItemRepository.save(new BorrowCartItem(book));
    }

    public void deleteBorrowCartItem(BorrowCart borrowCart, Long borrowCartItemId, Long bookId) {
        Optional<BorrowCartItem> optionalBorrowCartItem = borrowCart.getBorrowCartItems().stream()
                .filter(cartItem -> cartItem.getId().equals(borrowCartItemId))
                .findFirst();
        BorrowCartItem borrowCartItemToRemove = optionalBorrowCartItem.get();
        Optional<Book> optionalBook = bookService.findBookById(bookId);
        Book book = optionalBook.get();
        borrowCart.getBorrowCartItems().remove(borrowCartItemToRemove);
        borrowCartRepository.save(borrowCart);
        borrowCartItemRepository.findById(borrowCartItemId).ifPresent(borrowCartItem -> borrowCartItemRepository.delete(borrowCartItem));
        book.setIsBorrowed(false);
        bookRepository.save(book);
    }
}
