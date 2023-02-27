package server;

import entity.User;

/**
 * @author shijiu
 */

//服务端提供的接口
public interface UserService {
    User selectUserById(int userid);
}
