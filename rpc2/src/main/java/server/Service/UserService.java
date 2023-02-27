package server.Service;

import entity.User;

/**
 * @author shijiu
 */

//服务端提供的接口
public interface UserService {
    //通过id查询User
    User selectUserById(int userid);
    //插入一个User
    Integer insertUser(User user);
}
