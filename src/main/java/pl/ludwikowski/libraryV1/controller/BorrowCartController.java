package pl.ludwikowski.libraryV1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.ludwikowski.libraryV1.model.BorrowCart;
import pl.ludwikowski.libraryV1.model.LibraryUser;
import pl.ludwikowski.libraryV1.repository.LibraryUserRepository;
import pl.ludwikowski.libraryV1.service.BorrowCartService;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequestMapping("/cart")
public class BorrowCartController {

    BorrowCartService borrowCartService;

    LibraryUserRepository libraryUserRepository;

@Secured("USER")
    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")

    @Operation(summary = "Create or get cart")
    public BorrowCart createOrGetBorrowCart() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userEmail = securityContext.getAuthentication().getName();
        LibraryUser libraryUser = libraryUserRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User with email " + userEmail + " not found"));
        return borrowCartService.createOrGetBorrowCart(libraryUser);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Secured("USER")
    @Operation(summary = "Add book to cart")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Książka została dodana do koszyka."),
            @ApiResponse(responseCode = "400", description = "Limit osiągnięty lub książka jest już wypożyczona.",
                    content = @Content)
    })
    public BorrowCart addBookToBorrowCart(@RequestParam("bookId") Long bookId) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userEmail = securityContext.getAuthentication().getName();
        LibraryUser libraryUser = libraryUserRepository.findByEmail(userEmail).get();
        return borrowCartService.addBookToBorrowCart(libraryUser, bookId);
    }

    @DeleteMapping("returnBook")
    @SecurityRequirement(name = "Bearer Authentication")
    @Secured("USER")
    @Operation(summary = "Return book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Książka została zwrócona."),
            @ApiResponse(responseCode = "400", description = "Nie ma takiej książki w koszyku.",
                    content = @Content)
    })
    public void returnBook(@RequestParam("bookId") Long bookId) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userEmail = securityContext.getAuthentication().getName();
        LibraryUser libraryUser = libraryUserRepository.findByEmail(userEmail).get();
        borrowCartService.returnBook(libraryUser, bookId);
    }


    @GetMapping("/{libraryUserId}/can-borrow-more")
    @SecurityRequirement(name = "Bearer Authentication")
    @Secured("USER")
    @Operation(summary = "Checking if the user can borrow more books")
    public ResponseEntity<Boolean> canBorrowMoreBooks(@PathVariable Long libraryUserId) {
        boolean canBorrow = borrowCartService.canBorrowMoreBooks(libraryUserId);
        return ResponseEntity.ok(canBorrow);
    }
}
