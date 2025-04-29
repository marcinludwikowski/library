package pl.ludwikowski.libraryV1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;

    String title;

    String authorLastName;

    String authorFirstNames;

    Integer publicationYear;

    String category;

    Boolean isBorrowed = false;

    Integer borrowCount = 0;

    boolean deleted;

}
