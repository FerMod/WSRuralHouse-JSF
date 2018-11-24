package com.ruralhousejsf.model.login;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.security.auth.login.AccountNotFoundException;

import com.ruralhousejsf.exceptions.UserRoleException;

import businessLogic.ApplicationFacadeFactory;
import businessLogic.ApplicationFacadeInterface;
import configuration.Config;
import configuration.ConfigXML;
import domain.AbstractUser;
import domain.UserType;
import exceptions.AuthException;

@ManagedBean(name = "login")
@SessionScoped
public class LoginBean {

	private String user;
	private String pass;

	private AbstractUser client;

	private Config config;
	private ApplicationFacadeInterface aplicationFacade;

	public LoginBean(){
		System.out.println(getClass().getResource("/db/config.xml").getFile());
		config = ConfigXML.loadConfig(getClass().getResource("/db/config.xml").getFile());
		aplicationFacade = ApplicationFacadeFactory.createApplicationFacade(config);
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
		if (aplicationFacade.getTypeOfUser(user) != UserType.CLIENT || aplicationFacade.getTypeOfUser(user) != UserType.OWNER) {
			return aplicationFacade.login(getUser(), getPass());
		} else {
			throw new UserRoleException();
		}
	}

	public String validate() {

		boolean login = false;
		FacesContext fc = FacesContext.getCurrentInstance();

		try {
			client = login(user, pass);
			login = true;
		} catch(UserRoleException e) {
			failedValidationMsg("El rol de usuario no es adecuado.", fc, fc.getCurrentPhaseId().toString());
		} catch(AccountNotFoundException e) {
			failedValidationMsg("Cuenta no encontrada.", fc, fc.getCurrentPhaseId().toString());
		} catch(AuthException e) {
			failedValidationMsg("La contraseña o el usuario indicado es incorrecto.", fc, fc.getCurrentPhaseId().toString());
		}

		return login ? "ok" : "error";
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
			failedValidationMsg("El rol de usuario no es adecuado.", fc, uiInputUser.getClientId() + uiInputPass.getClientId());
		} catch(AccountNotFoundException e) {
			failedValidationMsg("Cuenta no encontrada.", fc, uiInputUser.getClientId() + uiInputPass.getClientId());
		} catch(AuthException e) {
			failedValidationMsg("La contraseña o el usuario indicado es incorrecto.", fc, uiInputUser.getClientId() + uiInputPass.getClientId());
		}

	}
	 */

	private void failedValidationMsg(String message, FacesContext fc, String clientId) {
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		fc.addMessage(clientId, msg);
		fc.validationFailed();
		fc.renderResponse();
	}


}
