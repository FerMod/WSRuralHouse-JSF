package com.ruralhousejsf.model.login;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.security.auth.login.AccountNotFoundException;

import com.ruralhousejsf.AppFacade;
import com.ruralhousejsf.exceptions.UserRoleException;

import businessLogic.ApplicationFacadeInterface;
import domain.AbstractUser;
import domain.UserType;
import exceptions.AuthException;

public class LoginBean {

	private String user;
	private String pass;

	private AbstractUser logedinUser;
	private AppFacade applicationFacade;
	
	public LoginBean(){
		applicationFacade = AppFacade.getInstance();
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
	
	public AbstractUser getLoggedUser() {
		return logedinUser;
	}

	public AbstractUser login(String user, String pass) throws UserRoleException, AccountNotFoundException, AuthException {		
		if (applicationFacade.getImpl().getTypeOfUser(user) != UserType.CLIENT || applicationFacade.getImpl().getTypeOfUser(user) != UserType.OWNER) {
			return applicationFacade.getImpl().login(getUser(), getPass());
		} else {
			throw new UserRoleException();
		}
	}

	public String validate() {

		boolean login = false;
		FacesContext fc = FacesContext.getCurrentInstance();

		try {
			logedinUser = login(user, pass);
			login = true;
		} catch(UserRoleException e) {
			failedValidationMsg("El rol de usuario no es adecuado.", fc, fc.getCurrentPhaseId().toString());
		} catch(AccountNotFoundException e) {
			failedValidationMsg("Cuenta no encontrada.", fc, fc.getCurrentPhaseId().toString());
		} catch(AuthException e) {
			failedValidationMsg("La contraseña o el usuario indicado es incorrecto.", fc, fc.getCurrentPhaseId().toString());
		}
		System.out.println(login ? "ok" : "error");
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
