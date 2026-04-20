package vn.hoidanit.springrestwithai.service;

import org.springframework.stereotype.Service;
import vn.hoidanit.springrestwithai.entity.User;
import vn.hoidanit.springrestwithai.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = new User();
        user.setId(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        return userRepository.update(user);
    }

    public boolean deleteUser(Long id) {
        return userRepository.deleteById(id);
    }
}
