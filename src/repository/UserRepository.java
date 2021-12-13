package repository;
import model.User;
import java.util.List;

public interface UserRepository {
    User findById (Long id);
    User findByUsername (String username);
    List<User> findAll();
    User create(User user);
    User update(User user);
    boolean deleteById(Long id);
}
