package pl.ludwikowski.libraryV1.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ludwikowski.libraryV1.model.LibraryUser;
import pl.ludwikowski.libraryV1.model.Role;
import pl.ludwikowski.libraryV1.repository.LibraryUserRepository;

import java.util.Optional;


@Service
@AllArgsConstructor
public class LibraryUserService implements UserDetailsService {

    LibraryUserRepository libraryUserRepository;
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LibraryUser> optionalLibraryUser = libraryUserRepository.findByEmail(username);
        return optionalLibraryUser.orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }

    public LibraryUser registerLibraryUser(LibraryUser libraryUser) {
        libraryUser.setPassword(passwordEncoder.encode(libraryUser.getPassword()));
        libraryUser.setRole(Role.USER);
        return libraryUserRepository.save(libraryUser);
    }
}
