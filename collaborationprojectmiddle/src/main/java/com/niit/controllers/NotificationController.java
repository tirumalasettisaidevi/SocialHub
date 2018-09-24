package com.niit.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.NotificationDao;
import com.niit.entity.ErrorClazz;
import com.niit.entity.Notification;


@Controller
public class NotificationController
{

	@Autowired
	private NotificationDao notificationDao;
		@RequestMapping(value="/notifications",method=RequestMethod.GET)
	public ResponseEntity<?> getNotificationsNotViewed(HttpSession session){
		String email=(String)session.getAttribute("loggedInUser");
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access.. please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<Notification> notifications=notificationDao.getNotificationNotViewed(email);
		return new ResponseEntity<List<Notification>>(notifications,HttpStatus.OK);
	}
		@RequestMapping(value="/notification/{id}",method=RequestMethod.GET)
		public ResponseEntity<?> getNotification(@PathVariable int id,HttpSession session){
			String email=(String)session.getAttribute("loggedInUser");
			if(email==null){
				ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access.. please login..");
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			}
			Notification notification=notificationDao.getNotification(id);
			return new ResponseEntity<Notification>(notification,HttpStatus.OK);
		}

}
