package server.Service;

import entity.Blog;

/**
 * @author shijiu
 */
public class BlogServiceImpl implements BlogService {

    @Override
    public Blog getBlogById(int id) {
        System.out.println("查询成功"+id);
        Blog blog = Blog.builder().userId(1).id(id).title("140").build();
        return blog;
    }
}
