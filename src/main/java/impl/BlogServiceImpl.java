package impl;

import entity.Blog;
import service.BlogService;

/**
 * @author shijiu
 */
public class BlogServiceImpl implements BlogService {


    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).userId(1).title("真的巨菜").build();
        System.out.println("客户端查询了id为"+id+"的博客");
        return blog;
    }
}
