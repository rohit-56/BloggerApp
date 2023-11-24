package com.example.BloggerApp.service;

import com.example.BloggerApp.http.request.TagRequest;
import com.example.BloggerApp.http.request.UpdateBlogRequest;
import com.example.BloggerApp.models.BlogEntity;
import com.example.BloggerApp.models.CategoryEntity;
import com.example.BloggerApp.models.TagEntity;
import com.example.BloggerApp.models.UserEntity;
import com.example.BloggerApp.repository.BlogRepository;
import com.example.BloggerApp.repository.CategoryRepository;
import com.example.BloggerApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BlogService {

    private BlogRepository blogRepository;

    private UserRepository userRepository;

    private CategoryRepository categoryRepository;

    public BlogService(BlogRepository blogRepository,UserRepository userRepository,CategoryRepository categoryRepository){
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }
    public BlogEntity addBlog(BlogEntity blogEntity,long userid,long categoryId){
        UserEntity userEntity = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("User Not Found"));
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category Not Found"));
        blogEntity.setUserEntity(userEntity);
        blogEntity.setCategoryEntity(categoryEntity);
        return blogRepository.save(blogEntity);
    }

    public List<BlogEntity> getBlogEntities(){
        return blogRepository.findAll();
    }

    public BlogEntity getBlogById(long id){
        return blogRepository.getById(id);
    }

    public void deleteBlogById(long id){
        blogRepository.deleteById(id);
    }

    public BlogEntity updateBlogEntityId(UpdateBlogRequest updateBlogRequest){
        BlogEntity blogEntity = blogRepository.getById(updateBlogRequest.getId());
        blogEntity.getTagEntities().clear();
        blogEntity.setTitle(updateBlogRequest.getTitle());
        blogEntity.setBody(updateBlogRequest.getBody());
        blogEntity.setImageCover(updateBlogRequest.getImageCover());
        blogEntity.setSubtitle(updateBlogRequest.getSubtitle());
        blogEntity.getTagEntities().addAll(updateBlogRequest.getTagRequests().stream().map(fromTagRequestToTagEntity).collect(Collectors.toList()));
       return blogRepository.save(blogEntity);
    }

    public List<TagEntity> getTagList(long blogId){
        BlogEntity blogEntity = blogRepository.getById(blogId);
        return blogEntity.getTagEntities();
    }

    private final Function<TagRequest,TagEntity> fromTagRequestToTagEntity =
            tagRequest -> {
          TagEntity tagEntity = new TagEntity();
          tagEntity.setTag(tagRequest.getTag());
          return tagEntity;
            };
}
