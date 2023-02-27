package server.Service;

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

    @Override
    public Integer insertUser(User user) {
        System.out.println("插入数据成功"+user.getUserId());
        return user.getUserId();
    }
}
