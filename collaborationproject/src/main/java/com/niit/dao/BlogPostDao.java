package com.niit.dao;

import java.util.List;

import com.niit.entity.BlogPost;

public interface BlogPostDao 
{

	void addBlogPost(BlogPost blogPost);
	List<BlogPost> getApprovedBlogs();
	List<BlogPost> getBlogsWaitingForApproval();
	BlogPost getBlogPost(int id);
	void updateBlogPost(BlogPost blogPost);
	void deleteBlogPost(BlogPost blogPost);


}
