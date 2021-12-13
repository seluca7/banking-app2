package service;

import model.LogEntry;

import java.util.List;

public interface LogService {
    List<LogEntry> findLogsByUsername(String username);
}
