package vn.hoidanit.springrestwithai.repository;

import vn.hoidanit.springrestwithai.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        // Khởi tạo sẵn 3 user mẫu
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Nguyen Van A");
        user1.setEmail("a@gmail.com");
        users.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Tran Thi B");
        user2.setEmail("b@gmail.com");
        users.add(user2);

        User user3 = new User();
        user3.setId(3L);
        user3.setName("Le Van C");
        user3.setEmail("c@gmail.com");
        users.add(user3);
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public User save(User user) {
        users.add(user);
        return user;
    }

    public User update(User updatedUser) {
        Optional<User> userOptional = findById(updatedUser.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return user;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
}
