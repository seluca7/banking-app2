package presentation;

import com.mysql.cj.log.Log;
import model.Account;
import model.LogEntry;
import model.User;
import repository.AccountRepository;
import repository.LogRepository;
import repository.UserRepository;
import service.AccountService;
import service.LogService;
import service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdminInterface extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JButton refreshButton;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton createButton;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField textField4;
    private JButton deleteButton;
    private JPanel label4;
    private JButton refreshButton1;
    private JTable table2;
    private JTable table3;
    private JButton refreshButton2;
    private JTextField textField5;
    private JButton searchButton;
    private JTable table4;

    public AdminInterface(Long currentUserId, AccountService accountService, AccountRepository accountRepository,
                          LogRepository logRepository, UserService userService, UserRepository userRepository, LogService logService) {

        add(mainPanel);
        setTitle("admin interface");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long id;

                if(textField4.getText().equals("")){
                    id = 0L;
                    System.out.println("campuri necompletate");
                }
                else{
                    id = Long.parseLong(textField4.getText());
                    userService.delete(id);
                    System.out.println("User-ul cu id-ul " + id + " a fost sters");
                }
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username;
                String password;
                Boolean admin;
                if(textField1.getText().equals("")||textField2.getText().equals("")||textField3.getText().equals("")){
                    System.out.println("campuri necompletate");
                }
                else{
                    username = textField1.getText();
                    password = textField2.getText();
                    admin = Boolean.valueOf(textField3.getText());
                    //admin = Integer.parseInt(textField3.getText());
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setAdmin(admin);
                    user = userService.save(user);
                    System.out.println("a fost creeat user-ul " + user.toString());
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnNames = { "Id",
                        "Username",
                        "Password",
                        "Admin"};
                List<User> users = userRepository.findAll();

                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnNames);
                table1.setModel(model);

                Object rowData[] = new Object[4];

                for(int i = 0;i<users.size();i++){
                    rowData[0] = users.get(i).getId();
                    rowData[1] = users.get(i).getUsername();
                    rowData[2] = users.get(i).getPassword();
                    rowData[3] = users.get(i).getAdmin();
                    model.addRow(rowData);
                }
            }

        });
        refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnNames = { "Account Id",
                        "Balance",
                        "User Id"};
                List<Account> accounts = accountRepository.findAll();
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnNames);
                table2.setModel(model);

                Object rowData[] = new Object[3];
                for(int i = 0;i<accounts.size();i++){
                    rowData[0] = accounts.get(i).getId();
                    rowData[1] = accounts.get(i).getBalance();
                    rowData[2] = accounts.get(i).getUserId();
                    model.addRow(rowData);
                }
            }
        });
        refreshButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnNames = { "Log Id",
                        "Descriprion",
                        "User Id"};
                List<LogEntry> logEntries = logRepository.findAll();
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnNames);
                table3.setModel(model);
                Object rowData[] = new Object[3];
                for(int i = 0;i<logEntries.size();i++){
                    rowData[0] = logEntries.get(i).getId();
                    rowData[1] = logEntries.get(i).getDescription();
                    rowData[2] = logEntries.get(i).getUserId();
                    model.addRow(rowData);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnNames = { "Log Id",
                        "Descriprion",
                        "User Id"};
                String username;
                if(textField5.getText().equals("")){
                    username = " ";
                    System.out.println("campuri necolpletate");
                }
                else {
                    username = textField5.getText();
                }
                List<LogEntry> logEntries= logService.findLogsByUsername(username);
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnNames);
                table4.setModel(model);
                Object rowData[] = new Object[3];
                for(int i = 0;i<logEntries.size();i++){
                    rowData[0] = logEntries.get(i).getId();
                    rowData[1] = logEntries.get(i).getDescription();
                    rowData[2] = logEntries.get(i).getUserId();
                    model.addRow(rowData);
                }
            }
        });

    }
}
