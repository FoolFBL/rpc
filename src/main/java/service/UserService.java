package service;

import entity.User;

/**
 * @author shijiu
 */
public interface UserService {
    User getUserById(Integer id);
    Integer insertUserId(User user);
}
