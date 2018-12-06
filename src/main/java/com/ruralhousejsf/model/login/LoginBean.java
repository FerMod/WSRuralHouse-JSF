package com.ruralhousejsf.model.login;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.security.auth.login.AccountNotFoundException;

import com.ruralhousejsf.domain.AbstractUser;
import com.ruralhousejsf.domain.UserType;
import com.ruralhousejsf.exceptions.AuthException;
import com.ruralhousejsf.exceptions.UserRoleException;

public class LoginBean {

	private String user;
	private String pass;

	private AbstractUser logedinUser;
	private ApplicationFacadeInterface applicationFacade;

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

		try {

			UserType userType = applicationFacade.getTypeOfUser(user);

			if (userType != UserType.CLIENT || userType != UserType.OWNER) {
				return applicationFacade.login(getUser(), getPass());
			} else {
				throw new UserRoleException();
			}

		} catch (AccountNotFoundException e) {
			throw e;
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

	private void failedValidationMsg(String message, FacesContext fc, String clientId) {
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		fc.addMessage(clientId, msg);
		fc.validationFailed();
		fc.renderResponse();
	}

}
