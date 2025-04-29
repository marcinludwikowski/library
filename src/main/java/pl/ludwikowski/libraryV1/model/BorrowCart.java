package pl.ludwikowski.libraryV1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class BorrowCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    LibraryUser libraryUser;

    @OneToMany
    List<BorrowCartItem> borrowCartItems = new ArrayList<>();

    Integer borrowCountByUser = 0;

    public static final int MAX_BORROW_LIMIT = 5;

    public BorrowCart(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }

}
