package com.niit.controllers;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.BlogPostDao;
import com.niit.dao.UserDao;
import com.niit.entity.BlogPost;
import com.niit.entity.ErrorClazz;
import com.niit.entity.User;



@Controller
public class BlogPostController 
{

	@Autowired
	private BlogPostDao blogPostDao;
		@Autowired
	private UserDao userDao;
		@RequestMapping(value="/addblogpost",method=RequestMethod.POST)
		public ResponseEntity<?> addBlogPost(@RequestBody BlogPost blogPost,HttpSession session){
			System.out.println(blogPost.getBlogTitle());
			String email=(String)session.getAttribute("loggedInUser");
			if(email==null){
				ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access.. please login..");
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			}
			blogPost.setPostedOn(new Date());
			
			blogPost.setPostedBy(userDao.getUser(email));
			try{
			blogPostDao.addBlogPost(blogPost);
			}catch(Exception e){
				ErrorClazz errorClazz=new ErrorClazz(6,"Unable to insert blogpost details.."+e.getMessage());
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
		}
		@RequestMapping(value="/approvedblogs",method=RequestMethod.GET)
		public ResponseEntity<?> getApprovedBlogs(HttpSession session){
			String email=(String)session.getAttribute("loggedInUser");
			if(email==null){
				ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access.. please login..");
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			}
			List<BlogPost> approvedBlogs=blogPostDao.getApprovedBlogs();
			return new ResponseEntity<List<BlogPost>>(approvedBlogs,HttpStatus.OK);
		}
		@RequestMapping(value="/getblog/{id}",method=RequestMethod.GET)
		public ResponseEntity<?> getBlog(@PathVariable int id,HttpSession session){
			String email=(String)session.getAttribute("loggedInUser");
			if(email==null){
				ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access.. please login..");
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			}
			BlogPost blogPost=blogPostDao.getBlogPost(id);
			return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
		}
		
		@RequestMapping(value="/blogswaitingforapproval",method=RequestMethod.GET)
		public ResponseEntity<?> getBlogsWaitingForApproval(HttpSession session){
//			//AUTHENTICATION
			String email=(String)session.getAttribute("loggedInUser");
			if(email==null){
				ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access.. please login..");
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			}
			//AUTHORIZATION - only admin can view list of blogs waiting for approval
			User user=userDao.getUser(email);
			
			if(!user.getRole().equals("ADMIN")){
				ErrorClazz errorClazz=new ErrorClazz(6,"Access Denied..");
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			}
			List<BlogPost> blogsWaitingForApproval=blogPostDao.getBlogsWaitingForApproval();
			return new ResponseEntity<List<BlogPost>>(blogsWaitingForApproval,HttpStatus.OK);
		}

}
