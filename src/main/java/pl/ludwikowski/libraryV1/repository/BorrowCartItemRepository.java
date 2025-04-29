package pl.ludwikowski.libraryV1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ludwikowski.libraryV1.model.BorrowCartItem;

@Repository
public interface BorrowCartItemRepository extends JpaRepository<BorrowCartItem, Long> {
}
