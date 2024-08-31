package engine.service;

import engine.exception.NonUniqueUserException;
import engine.mode.UserAdapter;
import engine.model.UserDTO;
import engine.repository.UserRepository;
import engine.service.mapper.UserMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserDetailsServiceImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public void save(UserDTO user) {
        try {
            userRepository.save(mapper.toEntity(user));
        } catch (DataIntegrityViolationException dive) {
            throw new NonUniqueUserException(user.email(), dive);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository
                .findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("%s not found".formatted(username)));

        return new UserAdapter(user);
    }
}
