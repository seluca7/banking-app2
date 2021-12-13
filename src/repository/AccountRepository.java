package repository;
import model.Account;
import java.util.List;

public interface AccountRepository {
    List<Account> findAll();
    boolean delete(Long id);
    Account create(Account account);
    Account update(Account account);
    Account findById(Long id);
    List<Account> findByUserId(Long id);
}
