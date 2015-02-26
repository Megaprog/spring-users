package users.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import users.model.User;
import users.repository.UserRepository;
import users.service.UserService;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    Pattern passwordPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])\\w{6,}$");

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        validatePasswords(user, errors);
        validateEmail(user, errors);
    }

    private void validatePasswords(User user, Errors errors) {
        if (!passwordPattern.matcher(user.getPassword()).find() && (userService.isNew(user) || !user.getPassword().isEmpty()) ) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

    private void validateEmail(User user, Errors errors) {
        if (userService.isNew(user) && userRepository.findOneByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "email.exists", "User with this email already exists");
        }
    }
}
