package pl.ludwikowski.libraryV1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ludwikowski.libraryV1.model.BorrowCart;
import pl.ludwikowski.libraryV1.model.LibraryUser;

import java.util.Optional;

@Repository
public interface BorrowCartRepository extends JpaRepository<BorrowCart, Long> {

    Optional<BorrowCart> findBorrowCartByLibraryUser(LibraryUser libraryUser);

    Optional<BorrowCart> findBorrowCartByLibraryUserId(Long libraryUserId);

}
