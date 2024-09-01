package engine.service.mapper;

import engine.entity.User;
import engine.model.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(UserDTO dto) {
        var user = new User();
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        return user;
    }
}
