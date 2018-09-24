package com.niit.dao;

import java.util.List;

import com.niit.entity.Notification;

public interface NotificationDao 
{

	void addNotification(Notification notification);
	List<Notification> getNotificationNotViewed(String email);
	Notification  getNotification(int id);
	void  updateNotification(int id);
}
