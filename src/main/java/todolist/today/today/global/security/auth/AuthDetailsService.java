package todolist.today.today.global.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import todolist.today.today.global.error.exception.security.InvalidTokenException;
import todolist.today.today.domain.model.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.existsById(username)) {
            return new AuthDetails(username);
        } else {
            throw new InvalidTokenException();
        }
    }

}
