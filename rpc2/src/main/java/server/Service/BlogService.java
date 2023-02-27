package server.Service;

import entity.Blog;

/**
 * @author shijiu
 */
//新的服务接口
public interface BlogService {
    Blog getBlogById(int id);
}
