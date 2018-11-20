package com.ruralhousejsf.model.login;

import javax.security.auth.login.AccountNotFoundException;

import com.ruralhousejsf.exceptions.UserRoleException;

import businessLogic.ApplicationFacadeFactory;
import businessLogic.ApplicationFacadeInterface;
import configuration.Config;
import configuration.ConfigXML;
import domain.AbstractUser;
import domain.UserType;
import exceptions.AuthException;


public class LoginBean {
	
	private String user;
	private String pass;
	private AbstractUser client;
	private Config cf;
	private ApplicationFacadeInterface aplicationFacade;
	
	public LoginBean(){
		cf = ConfigXML.getInstance();
		aplicationFacade = ApplicationFacadeFactory.createApplicationFacade(cf);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public AbstractUser login(String user, String pass) throws UserRoleException, AccountNotFoundException, AuthException {		
		if (aplicationFacade.getTypeOfUser(user) != UserType.CLIENT) {
			return aplicationFacade.login(getUser(), getPass());
		} else {
			throw new UserRoleException();
		}
	}
	
	public String comprobar() throws UserRoleException {
		//https://www.mkyong.com/jsf2/custom-validator-in-jsf-2-0/
		try {
			client = login(getUser(), getPass());
		} catch (AccountNotFoundException e) {
			e.printStackTrace();
		} catch (AuthException e) {
			e.printStackTrace();
		} catch (UserRoleException e) {
			
		}
		
		return "ok";
	}
	
}
