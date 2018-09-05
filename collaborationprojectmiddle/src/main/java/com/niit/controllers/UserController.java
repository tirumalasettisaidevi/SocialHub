package com.niit.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.UserDao;
import com.niit.entity.ErrorClazz;
import com.niit.entity.User;

@Controller
public class UserController 
{
	@Autowired
private UserDao userDao;
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<?> registration(@RequestBody User user)
	{
		try
		{
			if(!userDao.isEmailUnique(user.getEmail()))
			{
				ErrorClazz errorClazz=new ErrorClazz(2,"Email id already exists.. Please choose different Email id");
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			userDao.registration(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(1,"Something went wrong. "+e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.PUT)
	public ResponseEntity<?> login(@RequestBody User user,HttpSession session){
	User validUser=userDao.login(user);
	if(validUser==null){//Invalid credentials, Email/pwd is incorrect
		ErrorClazz errorClazz=new ErrorClazz(4,"Invalid email/password...");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
	}
	else{//valid credentials,valid email and password
		//update online status to true
		validUser.setOnline(true);
		userDao.updateUser(validUser);
		session.setAttribute("loggedInUser", validUser.getEmail());
		System.out.println("Session Id" + session.getId());
		System.out.println("Session Attribute " + session.getAttribute("loggedInUser"));
		System.out.println("Created On " + session.getCreationTime());
		return new ResponseEntity<User>(validUser,HttpStatus.OK);
	}
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.PUT)
    public ResponseEntity<?> logout(HttpSession session){
		String email=(String)session.getAttribute("loggedInUser");
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(4,"Unauthorized access... please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUser(email);
		user.setOnline(false);
		userDao.updateUser(user);
		session.removeAttribute("loggedInUser");
		session.invalidate();
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @RequestMapping(value="/updateprofile",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUserProfile(@RequestBody User user,HttpSession session){
		//CHECK for AUTHENTICATION
    	String email=(String)session.getAttribute("loggedInUser");
    	if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(4,"Unauthorized access... please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
    	try{
    	userDao.updateUser(user);
    	}catch(Exception e){
    		ErrorClazz errorClazz=new ErrorClazz(5,"Unable to update user details..");
    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	return new ResponseEntity<User>(user,HttpStatus.OK);
	}

	
   
}
