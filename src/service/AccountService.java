package service;
import model.Account;
import java.util.List;

public interface AccountService {
    boolean addMoney(Long userId,Long accId, double amount);
    boolean getMoney(Long userId,Long accId, double amount);
    boolean transfer(Long userId,Long accId, Long receiverAccId, double amount);
    List<Account> findAccByUsername(String username);
    Account save(Account account);
    boolean delete(Long accId,Long userId);
}
