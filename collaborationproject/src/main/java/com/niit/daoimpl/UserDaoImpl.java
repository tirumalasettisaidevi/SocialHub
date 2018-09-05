package com.niit.daoimpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.dao.UserDao;
import com.niit.entity.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	public UserDaoImpl()
	{
		System.out.println("UserDaoImpl bean is created");
	}

	public void registration(User user)
	{
		Session session=sessionFactory.getCurrentSession();
		session.save(user);
	}

	public boolean isEmailUnique(String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User where email=?");
		query.setString(0, email);
		User user=(User)query.uniqueResult();
		if(user!=null){
			return false;
			
		}
		else
		{
			return true;
			
		}
	}

	public User login(User user) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User where email=? and password=?");
		query.setString(0, user.getEmail());
		query.setString(1, user.getPassword());
		
		return (User)query.uniqueResult();
		
	}

	public void updateUser(User user) 
	{
		Session session=sessionFactory.getCurrentSession();
		session.update(user);
		//update User_s190035 set password=?,firstname=?,lastname=?,phonenumber=?
		//, online_status=?,role=? where email=?
		
		
	}

	public User getUser(String email) 
	{
		Session session=sessionFactory.getCurrentSession();
		User user=(User)session.get(User.class, email);
		return user;

	
	}
	
	
	
	

}
