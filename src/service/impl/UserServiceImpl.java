package service.impl;

import model.User;
import service.UserService;
import repository.UserRepository;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if(user!=null){
            if(user.getPassword().equals(password)){
                System.out.println("user " + username + " login succes");
                return user;
            }
        }
        return null;
    }

    @Override
    public User save(User user) {
        if(user.getId()!=null){
            return userRepository.update(user);
        }
        else {
            return userRepository.create(user);
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
