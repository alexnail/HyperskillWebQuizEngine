package engine.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public record UserDTO(
        @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@(.+\\..+)$",
                message = "Invalid email format"
        ) String email,
        @Length(min = 5) String password) {
}
