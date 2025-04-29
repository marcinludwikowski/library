package pl.ludwikowski.libraryV1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.ludwikowski.libraryV1.model.LibraryUser;
import pl.ludwikowski.libraryV1.service.LibraryUserService;

import java.util.Collection;


@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequestMapping("/user")
public class LibraryUserController {


    LibraryUserService libraryUserService;

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Register new user")
    public LibraryUser registerUser(@RequestBody LibraryUser user) {
        return libraryUserService.registerLibraryUser(user);
    }

    @GetMapping()
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get own user email from Security Context")
    public String getUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        return securityContext.getAuthentication().getName() + " ---> " + authorities.stream().findFirst();
    }

}
