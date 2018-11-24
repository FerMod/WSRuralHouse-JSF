package com.ruralhousejsf.model.login;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
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

	/*private AbstractUser client;

	private Config cf;
	private ApplicationFacadeInterface aplicationFacade;*/

	public LoginBean(){
		/*cf = ConfigXML.getInstance();
		aplicationFacade = ApplicationFacadeFactory.createApplicationFacade(cf);*/
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

	/*public AbstractUser login(String user, String pass) throws UserRoleException, AccountNotFoundException, AuthException {		
		if (aplicationFacade.getTypeOfUser(user) != UserType.CLIENT) {
			return aplicationFacade.login(getUser(), getPass());
		} else {
			throw new UserRoleException();
		}
	}*/

	public String entrar() {
		return "ok";
	}
/*
	public void validateLogin(ComponentSystemEvent event) {

		FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent components = event.getComponent();

		UIInput uiInputUser = (UIInput) components.findComponent("login:username");
		UIInput uiInputPass = (UIInput) components.findComponent("login:password");
		String password = uiInputPass.getLocalValue() == null ? "" : uiInputPass.getLocalValue().toString();
		String user = uiInputUser.getLocalValue() == null ? "" : uiInputUser.getLocalValue().toString();


		try {
			client = login(user, password);
		} catch(UserRoleException e) {
			FacesMessage msg = new FacesMessage("El rol de usuario no es adecuado.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(uiInputUser.getClientId()+uiInputPass.getClientId(), msg);
			fc.validationFailed();
			fc.renderResponse();
		} catch(AccountNotFoundException e) {
			FacesMessage msg = new FacesMessage("Cuenta no encontrada.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(uiInputUser.getClientId()+uiInputPass.getClientId(), msg);
			fc.validationFailed();
			fc.renderResponse();
		} catch(AuthException e) {
			FacesMessage msg = new FacesMessage("La contraseña o el usuario indicado es incorrecto.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(uiInputUser.getClientId()+uiInputPass.getClientId(), msg);
			fc.validationFailed();
			fc.renderResponse();
		}
		 

	}
*/

}
