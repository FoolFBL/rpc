package server;

import entity.User;

/**
 * @author shijiu
 */

public class UserServiceImpl implements UserService {
    @Override
    public User selectUserById(int userId) {
        System.out.println("客户端查询了userid为"+userId+"的用户");
        User user = new User();
        user.setUserId(userId);
        user.setUserName("永远的神");
        return user;
    }
}
