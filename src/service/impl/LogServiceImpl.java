package service.impl;

import model.LogEntry;
import model.User;
import repository.LogRepository;
import repository.UserRepository;
import service.LogService;

import java.util.ArrayList;
import java.util.List;

public class LogServiceImpl implements LogService {
    UserRepository userRepository;
    LogRepository logRepository;

    public LogServiceImpl(UserRepository userRepository, LogRepository logRepository){
        this.userRepository = userRepository;
        this.logRepository = logRepository;
    }
    @Override
    public List<LogEntry> findLogsByUsername(String username) {
        User user = userRepository.findByUsername(username);
        List<LogEntry> logEntries = new ArrayList<>();
        if(logRepository.findByUserId(user.getId()).equals(null)){
            return null;
        }
        else {
            logEntries = logRepository.findByUserId(user.getId());
            return logEntries;
        }
    }
}
