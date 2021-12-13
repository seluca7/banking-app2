package repository.impl;

import model.LogEntry;
import repository.LogRepository;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;

public class LogRepositoryImpl implements LogRepository {
    private final JDBConnectionWrapper jdbConnectionWrapper;

    public LogRepositoryImpl(JDBConnectionWrapper jdbConnectionWrapper) {
        this.jdbConnectionWrapper = jdbConnectionWrapper;
    }

    @Override
    public List<LogEntry> findAll() {
        Connection connection = jdbConnectionWrapper.getConnection();
        List<LogEntry> logEntries = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM log");

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                LogEntry logEntry = new LogEntry();

                logEntry.setId(resultSet.getLong(1));
                logEntry.setDescription(resultSet.getString(2));
                logEntry.setUserId(resultSet.getLong(3));

                logEntries.add(logEntry);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return logEntries;
    }

    @Override
    public boolean delete(Long id) {
        Connection connection = jdbConnectionWrapper.getConnection();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM log WHERE id=?");
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
    public LogEntry create(LogEntry log) {
        Connection connection = jdbConnectionWrapper.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO log (description,userId) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,log.getDescription());
            preparedStatement.setLong(2,log.getUserId());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next())
            {
                log.setId(resultSet.getLong(1));
                return log;
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LogEntry update(LogEntry log) {
        Connection connection = jdbConnectionWrapper.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE log SET description=?, userId=? WHERE id=?");

            preparedStatement.setString(1,log.getDescription());
            preparedStatement.setLong(2,log.getUserId());
            preparedStatement.setLong(3,log.getId());

            int updatedRows = preparedStatement.executeUpdate();

            if(updatedRows>0){
                return log;
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
    public LogEntry findById(Long id) {
        Connection connection = jdbConnectionWrapper.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM log WHERE id=?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LogEntry logEntry = new LogEntry();

                logEntry.setId(resultSet.getLong(1));
                logEntry.setDescription(resultSet.getString(2));
                logEntry.setUserId(resultSet.getLong(3));
                return logEntry;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LogEntry> findByUserId(Long id) {
        Connection connection = jdbConnectionWrapper.getConnection();
        List<LogEntry> logEntries = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM log WHERE userId=?");
            preparedStatement.setLong(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                LogEntry logEntry = new LogEntry();

                logEntry.setId(resultSet.getLong(1));
                logEntry.setDescription(resultSet.getString(2));
                logEntry.setUserId(resultSet.getLong(3));

                logEntries.add(logEntry);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return logEntries;
    }

}
