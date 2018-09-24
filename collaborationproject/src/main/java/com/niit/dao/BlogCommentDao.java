package com.niit.dao;

import java.util.List;

import com.niit.entity.BlogComment;


public interface BlogCommentDao 
{

	void addBlogComment(BlogComment blogComment );
	List<BlogComment> getBlogComments(int blogPostId);
}
