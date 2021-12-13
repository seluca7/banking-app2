package service;
import model.User;

public interface UserService {
    User login(String username, String password);

    User save(User user);

    void delete(Long id);
}
