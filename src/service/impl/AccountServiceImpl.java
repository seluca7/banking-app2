package service.impl;

import model.Account;
import model.LogEntry;
import model.User;
import service.AccountService;
import repository.UserRepository;
import repository.AccountRepository;
import repository.LogRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    UserRepository userRepository;
    AccountRepository accountRepository;
    LogRepository logRepository;


    public AccountServiceImpl(UserRepository userRepository,AccountRepository accountRepository, LogRepository logRepository){
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.logRepository = logRepository;
    }
    public Account save(Account account){
        if(account.getId()!=null){
            return accountRepository.update(account);
        }
        else {
            return accountRepository.create(account);
        }
    }
    @Override
    public boolean addMoney(Long userId, Long accId, double amount) {
        Account acc = accountRepository.findById(accId);
        LogEntry logEntry = new LogEntry();
        if(acc.getUserId()==userId){
            acc.setBalance(acc.getBalance()+amount);
            accountRepository.update(acc);
            String logMessage = "S-a realizat o depunere de bani in contul " + acc.getId() + " apartinand persoanei cu id-ul "
                    + userId + ",suma:" + amount;
            logEntry.setUserId(userId);
            logEntry.setDescription(logMessage);
            logEntry = logRepository.create(logEntry);
            return true;
        }
        else {
            System.out.println("accesul la acest cont nu este permis");
            return false;
        }
    }

    @Override
    public boolean getMoney(Long userId, Long accId, double amount) {
        Account acc = accountRepository.findById(accId);
        LogEntry logEntry = new LogEntry();
        if(acc.getUserId()==userId){
            if(acc.getBalance()>=amount){
                acc.setBalance(acc.getBalance()-amount);
                accountRepository.update(acc);
                String logMessage = "S-a realizat o extragere de bani din contul " + acc.getId() + " apartinand persoanei cu id-ul "
                        + userId + ",suma:" + amount;
                logEntry.setUserId(userId);
                logEntry.setDescription(logMessage);
                logEntry = logRepository.create(logEntry);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            System.out.println("accesul la acest cont nu este permis");
            return false;
        }
    }

    @Override
    public boolean transfer(Long userId, Long accId, Long receiverAccId, double amount) {
        Account acc = accountRepository.findById(accId);
        Account acc2 = accountRepository.findById(receiverAccId);
        LogEntry logEntry = new LogEntry();
        if(acc.getUserId()==userId){

            acc.setBalance(acc.getBalance()-amount);
            acc2.setBalance(acc2.getBalance()+amount);

            accountRepository.update(acc);
            accountRepository.update(acc2);

            String logMessage = "S-a realizat un transfer de bani din contul " + acc.getId() + " apartinand persoanei cu id-ul "
                    + userId + " spre contul " + receiverAccId + ",suma:" + amount;
            logEntry.setUserId(userId);
            logEntry.setDescription(logMessage);
            logEntry = logRepository.create(logEntry);
            return true;
        }
        else{
            System.out.println("accesul la acest cont nu este permis");
            return false;
        }
    }

    @Override
    public List<Account> findAccByUsername(String username) {
        User user = userRepository.findByUsername(username);
        List<Account> accounts = new ArrayList<>();
        if(accountRepository.findByUserId(user.getId()).equals(null)){
            return null;
        }
        else {
            accounts = accountRepository.findByUserId(user.getId());
            return accounts;
        }
    }
    public boolean delete(Long accId,Long userId){
        Account account = accountRepository.findById(accId);
        if(account.getUserId()==userId){
            accountRepository.delete(accId);
            return true;
        }
        else{
            System.out.println("accesul la acest cont nu este permis");
            return false;
        }
    }
}
