package impl;

import entity.User;
import service.UserService;

import java.util.Random;

/**
 * @author shijiu
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id) {
        System.out.println("客户端查询了id为"+id+"的客户");
        Random random = new Random();
        User user = User.builder().name("李松").sex('M').id(1).build();
        return user;
    }

    @Override
    public Integer insertUserId(User user) {
        System.out.println("插入数据成功");
        return 1;
    }
}
