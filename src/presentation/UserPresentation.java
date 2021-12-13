package presentation;
import model.Account;
import repository.AccountRepository;
import repository.LogRepository;
import repository.impl.AccountRepositoryImpl;
import repository.impl.JDBConnectionWrapper;
import repository.impl.LogRepositoryImpl;
import service.AccountService;
import service.LogService;
import service.UserService;
import service.impl.AccountServiceImpl;
import service.impl.LogServiceImpl;
import service.impl.UserServiceImpl;
import model.User;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;

import javax.swing.*;
import java.awt.*;


public class UserPresentation {
    private UserService userService;

    public UserPresentation (UserService userService) {
        this.userService = userService;
    }
    //interfata grafica


    public void loginTest() {
        String username = "user";
        String password = "password";

        User user = userService.login(username, password);

        System.out.println(user);
    }

    public static void main(String[] args) {
        //instantiem repo
        String schema = "bank";
        JDBConnectionWrapper jdbConnectionWrapper = new JDBConnectionWrapper(schema);
        UserRepository userRepository = new UserRepositoryImpl(jdbConnectionWrapper);
        AccountRepository accountRepository = new AccountRepositoryImpl(jdbConnectionWrapper);
        LogRepository logRepository = new LogRepositoryImpl(jdbConnectionWrapper);
        AccountService accountService = new AccountServiceImpl(userRepository,accountRepository,logRepository);
        LogService logService = new LogServiceImpl(userRepository,logRepository);

        //instantiem service
        UserService userService = new UserServiceImpl(userRepository);

        //instantiem presentation / controller
       // UserPresentation userPresentation = new UserPresentation(userService);

        //userPresentation.loginTest();
        //User user = userRepository.findById(3L);



      //  boolean ok;
      //  ok = accountService.transfer(3L,1L,2L,500);
       //System.out.println(ok);
       // RegularUserInterface ui = new RegularUserInterface(user.getId(),accountService,accountRepository,logRepository);
       // ui.setVisible(true);
        //AdminInterface adminInterface = new AdminInterface(user.getId(),accountService,accountRepository,logRepository,
        //        userService,userRepository,logService);
        //adminInterface.setVisible(true);
        PresentationWindows loginScreen = new PresentationWindows();
        loginScreen.loginWindow(accountService,accountRepository,logRepository,userService,userRepository,
                logService);


    }
    }



