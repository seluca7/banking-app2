package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Account;
import model.LogEntry;
import model.User;
import repository.AccountRepository;
import repository.LogRepository;
import service.AccountService;

public class RegularUserInterface extends JFrame{
    private JTabbedPane tabbedPane1;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JLabel warningLabel1;
    private JButton okButton;
    private JLabel label3;
    private JTextField textField3;
    private JLabel label4;
    private JTextField textField4;
    private JLabel warningLabel2;
    private JButton okButton2;
    private JLabel label5;
    private JTextField textField5;
    private JTextField textField7;
    private JLabel label7;
    private JButton okButton3;
    private JLabel label6;
    private JTextField textField6;
    private JPanel mainPanel;
    private JButton refreshButton;
    private JTable table1;
    private JButton refreshButton2;
    private JTable table2;
    private JTextField textField8;
    private JLabel initBalanceLabel;
    private JButton createButton;
    private JTextField textField9;
    private JButton deleteButton;


    public RegularUserInterface(Long currentUserId, AccountService accountService, AccountRepository accountRepository, LogRepository logRepository){

        add(mainPanel);
        setTitle("user interface");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long accId;
                Double amount;
                if(textField1.getText().equals("")||textField2.getText().equals("")){
                    accId = 0L;
                    amount = 0.0;
                    System.out.println("campuri necompletate");
                }
                else {
                    accId = Long.parseLong(textField1.getText());
                    amount = Double.parseDouble(textField2.getText());
                    accountService.addMoney(currentUserId,accId,amount);
                    System.out.println("s-a adaugat suma " + amount + " in contul " + accId);
                }

            }
        });
        okButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long accId;
                Double amount;
                if(textField3.getText().equals("")||textField4.getText().equals("")){
                    accId = 0L;
                    amount = 0.0;
                    System.out.println("campuri necompletate");
                }
                else {
                    accId = Long.parseLong(textField3.getText());
                    amount = Double.parseDouble(textField4.getText());
                    accountService.getMoney(currentUserId,accId,amount);
                    System.out.println("s-a retras suma " + amount + " din contul " + accId);
                }
            }
        });
        okButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long accId;
                Double amount;
                Long receiverAccId;
                if(textField5.getText().equals("")||textField6.getText().equals("")||textField7.getText().equals("")){
                    accId = 0L;
                    amount = 0.0;
                    receiverAccId = 0L;
                    System.out.println("campuri necompletate");
                }
                else {
                    accId = Long.parseLong(textField5.getText());
                    receiverAccId = Long.parseLong(textField6.getText());
                    amount = Double.parseDouble(textField7.getText());
                    accountService.transfer(currentUserId,accId,receiverAccId,amount);
                    System.out.println("s-a transferat suma " + amount + " din contul " + accId + " in contul " + receiverAccId);
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnNames = {"Account Id",
                        "Balance",
                        "User Id"};
                List<Account> accounts= accountRepository.findByUserId(currentUserId);

                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnNames);
                table1.setModel(model);
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
                String[] columnNames = {"Log Id",
                        "Description",
                        "User Id"};
                List<LogEntry> logEntries= logRepository.findByUserId(currentUserId);

                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnNames);
                table2.setModel(model);
                Object rowData[] = new Object[3];

                for(int i = 0;i<logEntries.size();i++){
                    rowData[0] = logEntries.get(i).getId();
                    rowData[1] = logEntries.get(i).getDescription();
                    rowData[2] = logEntries.get(i).getUserId();
                    model.addRow(rowData);
                }
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double initialBalance;
                if(textField8.getText().equals("")){
                    initialBalance = 0.0;
                    System.out.println("campuri necompletate");
                }
                else
                {
                    initialBalance = Double.parseDouble(textField8.getText());
                    Account account = new Account();
                    account.setUserId(currentUserId);
                    account.setBalance(initialBalance);
                    account = accountService.save(account);
                    System.out.println("s-a creat contul cu nr " + account.getId() + " al utilizatorului " + currentUserId +
                            " avand soldul initial " + account.getBalance());
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long accountNr;
                if(textField9.getText().equals("")){
                    accountNr = 0L;
                    System.out.println("campuri necompletate");
                }
                else {
                    accountNr = Long.parseLong(textField9.getText());
                    accountService.delete(accountNr,currentUserId);
                    System.out.println("s-a sters contul cu nr " + accountNr + "apartinand user-ului " + currentUserId);
                }
            }
        });
    }
}
