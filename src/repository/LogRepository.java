package repository;

import model.LogEntry;

import java.util.List;

public interface LogRepository {
    List<LogEntry> findAll();
    boolean delete(Long id);
    LogEntry create(LogEntry logEntry);
    LogEntry update(LogEntry logEntry);
    LogEntry findById(Long id);
    List<LogEntry> findByUserId(Long id);
}
