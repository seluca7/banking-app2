package presentation;

import model.User;
import repository.AccountRepository;
import repository.LogRepository;
import repository.UserRepository;
import service.AccountService;
import service.LogService;
import service.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PresentationWindows{
    public void loginWindow(AccountService accountService, AccountRepository accountRepository,
                            LogRepository logRepository, UserService userService, UserRepository userRepository,
                            LogService logService){
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        //Creating the MenuBar and adding components

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        panel.setLayout(null);
        JLabel label1 = new JLabel("Enter username");
        JLabel label2 = new JLabel("Enter password");
        JTextField tf = new JTextField();
        JPasswordField pf = new JPasswordField();
        JButton lg = new JButton("Login");

        label1.setSize(200,30);
        label2.setSize(200,30);
        tf.setSize(200,30);
        pf.setSize(200,30);
        lg.setSize(100,40);

        label1.setLocation(50,10);
        tf.setLocation(40,50);
        label2.setLocation(50,90);
        pf.setLocation(40,140);
        lg.setLocation(80,190);

        panel.add(label1);
        panel.add(tf);
        panel.add(label2);
        panel.add(pf);
        panel.add(lg);

        //Adding Components to the frame.
        frame.getContentPane().add(panel);
        frame.setVisible(true);

        lg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String username;
                    String password;
                    if(tf.getText().equals("")){
                        username = " ";
                        password = " ";
                        System.out.println("campuri necompletate");
                    }
                    else {
                        username = tf.getText();
                        password = pf.getText();
                        User user = userService.login(username,password);
                        if (user!=null) {
                            if (user.getAdmin()) {
                                AdminInterface adminInterface = new AdminInterface(user.getId(), accountService, accountRepository, logRepository,
                                        userService, userRepository, logService);
                                adminInterface.setVisible(true);
                            }
                            else{
                                RegularUserInterface ui = new RegularUserInterface(user.getId(),accountService,accountRepository,logRepository);
                                ui.setVisible(true);
                            }
                        }
                        else {
                            System.out.println("login failed");
                        }
                    }
            }
        });
    }

}
