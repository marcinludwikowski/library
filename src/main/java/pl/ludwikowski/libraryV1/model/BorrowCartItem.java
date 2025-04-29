package pl.ludwikowski.libraryV1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class BorrowCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @ManyToOne
    Book book;


    public BorrowCartItem(Book book) {
        this.book = book;
    }

}
