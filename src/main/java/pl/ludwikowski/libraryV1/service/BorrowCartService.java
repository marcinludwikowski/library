package pl.ludwikowski.libraryV1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ludwikowski.libraryV1.config.BorrowCartException;
import pl.ludwikowski.libraryV1.model.Book;
import pl.ludwikowski.libraryV1.model.BorrowCart;
import pl.ludwikowski.libraryV1.model.BorrowCartItem;
import pl.ludwikowski.libraryV1.model.LibraryUser;
import pl.ludwikowski.libraryV1.repository.BookRepository;
import pl.ludwikowski.libraryV1.repository.BorrowCartItemRepository;
import pl.ludwikowski.libraryV1.repository.BorrowCartRepository;
import pl.ludwikowski.libraryV1.repository.LibraryUserRepository;

import java.util.List;
import java.util.Optional;

import static pl.ludwikowski.libraryV1.model.BorrowCart.MAX_BORROW_LIMIT;


@Service
@AllArgsConstructor
public class BorrowCartService {

    BookService bookService;

    BookRepository bookRepository;

    BorrowCartRepository borrowCartRepository;

    LibraryUserRepository libraryUserRepository;

    LibraryUserService libraryUserService;

    BorrowCartItemRepository borrowCartItemRepository;

    BorrowCartItemService borrowCartItemService;

    public BorrowCart createOrGetBorrowCart(LibraryUser libraryUser) {

        Optional<BorrowCart> optionalLibraryUserBorrowCart = borrowCartRepository.findBorrowCartByLibraryUser(libraryUser);
        return optionalLibraryUserBorrowCart.orElseGet(() -> borrowCartRepository.save(new BorrowCart(libraryUserRepository.save(libraryUser))));
    }

    public BorrowCart addBookToBorrowCart(LibraryUser libraryUser, Long bookId) {
        Optional<BorrowCart> optionalBorrowCart = borrowCartRepository.findBorrowCartByLibraryUser(libraryUser);
        Optional<Book> optionalBook = bookService.findBookById(bookId);
        if (optionalBorrowCart.isPresent() && optionalBook.isPresent()) {
            BorrowCart borrowCart = optionalBorrowCart.get();
            Book book = optionalBook.get();
            List<BorrowCartItem> borrowCartItems = borrowCart.getBorrowCartItems();
            if (!book.getIsBorrowed() && borrowCartItems.size() < MAX_BORROW_LIMIT) {
                BorrowCartItem borrowCartItem = borrowCartItemService.createBorrowCartItem(book);
                borrowCartItems.add(borrowCartItem);
                book.setIsBorrowed(true);
                book.setBorrowCount(book.getBorrowCount() + 1);
                bookRepository.save(book);
                borrowCart.setBorrowCountByUser(borrowCart.getBorrowCountByUser() + 1);
            }
            else {
                throw new BorrowCartException("Limit osiągnięty lub książka jest już wypożyczona.");
            }
            return borrowCartRepository.save(borrowCart);
        }
        return new BorrowCart();

    }


    public void returnBook(LibraryUser libraryUser, Long bookId) {
        Optional<BorrowCart> optionalBorrowCart = borrowCartRepository.findBorrowCartByLibraryUser(libraryUser);
        Optional<Book> optionalBook = bookService.findBookById(bookId);
        if (optionalBorrowCart.isPresent() && optionalBook.isPresent()) {
            BorrowCart borrowCart = optionalBorrowCart.get();
            Book book = optionalBook.get();
            List<BorrowCartItem> borrowCartItems = borrowCart.getBorrowCartItems();
            Optional<BorrowCartItem> optionalBorrowCartItem = borrowCartItems.stream().filter(borrowCartItem -> borrowCartItem.getBook().equals(book)).findFirst();
            if (optionalBorrowCartItem.isPresent()) {
                BorrowCartItem borrowCartItem = optionalBorrowCartItem.get();
                borrowCartItemService.deleteBorrowCartItem(borrowCart, borrowCartItem.getId(), bookId);
                book.setIsBorrowed(false);
                bookRepository.save(book);
                borrowCart.setBorrowCountByUser(borrowCart.getBorrowCountByUser() - 1);
                borrowCartRepository.save(borrowCart);
            } else {
                throw new BorrowCartException("Nie ma takiej książki w koszyku.");
            }
        }
    }


    public boolean canBorrowMoreBooks(Long libraryUserId) {
        Optional<BorrowCart> optionalBorrowCart = borrowCartRepository.findBorrowCartByLibraryUserId(libraryUserId);
        BorrowCart borrowCart = optionalBorrowCart.get();
        return borrowCart.getBorrowCountByUser() < MAX_BORROW_LIMIT;
    }

}
