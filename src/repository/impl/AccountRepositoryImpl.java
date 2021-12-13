package repository.impl;

import model.Account;
import repository.AccountRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;



@SuppressWarnings("ALL")
public class AccountRepositoryImpl implements AccountRepository {
    private final JDBConnectionWrapper jdbConnectionWrapper;

    public AccountRepositoryImpl(JDBConnectionWrapper jdbConnectionWrapper) {
        this.jdbConnectionWrapper = jdbConnectionWrapper;
    }
    @Override
    public List<Account> findAll() {
        Connection connection = jdbConnectionWrapper.getConnection();
        List<Account> accounts = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account");

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Account acc = new Account();

                acc.setId(resultSet.getLong(1));
                acc.setBalance(resultSet.getDouble(2));
                acc.setUserId(resultSet.getLong(3));

                accounts.add(acc);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public boolean delete(Long id) {
        Connection connection = jdbConnectionWrapper.getConnection();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM account WHERE id=?");
            preparedStatement.setLong(1, id);

            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows>0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account create(Account account) {

        Connection connection = jdbConnectionWrapper.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account (balance,userId) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1,account.getBalance());
            preparedStatement.setLong(2,account.getUserId());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next())
            {
                account.setId(resultSet.getLong(1));
                return account;
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account update(Account account) {
        Connection connection = jdbConnectionWrapper.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE account SET balance=?, userId=? WHERE id=?");

            preparedStatement.setDouble(1,account.getBalance());
            preparedStatement.setLong(2,account.getUserId());
            preparedStatement.setLong(3,account.getId());

            int updatedRows = preparedStatement.executeUpdate();

            if(updatedRows>0){
                return account;
            }
            else {
                return null;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account findById(Long id) {
        Connection connection = jdbConnectionWrapper.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE id=?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Account acc = new Account();

                acc.setId(resultSet.getLong(1));
                acc.setBalance(resultSet.getDouble(2));
                acc.setUserId(resultSet.getLong(3));
                return acc;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Account> findByUserId(Long id) {
        Connection connection = jdbConnectionWrapper.getConnection();
        List<Account> accounts = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE userId=?");
            preparedStatement.setLong(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Account acc = new Account();

                acc.setId(resultSet.getLong(1));
                acc.setBalance(resultSet.getDouble(2));
                acc.setUserId(resultSet.getLong(3));

                accounts.add(acc);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return accounts;
    }
}
