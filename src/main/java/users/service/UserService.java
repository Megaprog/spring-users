package users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import users.model.User;
import users.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean isNew(User user) {
        return user.getId() == 0;
    }

    public User createOrUpdate(User user) {
        final boolean isNew = isNew(user);

        if (isNew || !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (!isNew) {
            final User existingUser = userRepository.findOne(user.getId());
            user.setCreated(existingUser.getCreated());
            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            }
        }

        userRepository.saveAndFlush(user);
        return user;
    }
}
