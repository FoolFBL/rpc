package com.kong.rpc7.service;


import com.kong.rpc7.common.Blog;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        int a = 3 / 0;
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        System.out.println("客户端查询了"+id+"博客");

        return blog;
    }
}
