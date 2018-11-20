package model.login;

import javax.security.auth.login.AccountNotFoundException;

import businessLogic.ApplicationFacadeFactory;
import businessLogic.ApplicationFacadeInterface;
import configuration.Config;
import configuration.ConfigXML;
import domain.AbstractUser;
import domain.UserType;
import exceptions.AuthException;


public class LoginBean {
	public class UserRoleException extends Exception {
		public UserRoleException() {
			super(); 
		}
	}
	
	String user;
	String pass;
	AbstractUser client;
	Config cf = ConfigXML.getInstance();
	ApplicationFacadeInterface aplicationFacade = ApplicationFacadeFactory.createApplicationFacade(cf);
	
	public LoginBean(){
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
		client = aplicationFacade.login(getUser(), getPass());
		if (client.getRole() != UserType.CLIENT) {
			return client;
		} else {
			throw new UserRoleException();
		}
	}
	
	public String comprobar() throws UserRoleException {
		//https://www.mkyong.com/jsf2/custom-validator-in-jsf-2-0/
		try {
			client = login(getUser(), getPass());
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserRoleException e) {
			
		}
		
		return "ok";
	}
	
	
}
