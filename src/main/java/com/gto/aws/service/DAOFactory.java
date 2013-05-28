package com.gto.aws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basho.riak.client.IRiakClient;
import com.gto.aws.model.User;


public class DAOFactory {
 
	
	public interface IUserDAO extends IGenericDAO<User> {}
		
	
	// The use those interfaces as we declare entity-specific DAOs

	public static class UserDAO extends GenericDAO<User> implements IUserDAO {
		public UserDAO(){
			super("test_user");
		}
	}
	
	
 
	// Static-only usage pattern
	protected DAOFactory() {}
	
	public static IUserDAO getUserDao() {
		return new UserDAO();
	}
	

}