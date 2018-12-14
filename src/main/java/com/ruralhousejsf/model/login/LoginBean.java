package com.ruralhousejsf.model.login;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


import com.ruralhousejsf.businessLogic.AppFacade;
import com.ruralhousejsf.businessLogic.ApplicationFacadeInterface;

public class LoginBean {

	private String user;
	private String pass;

	private ApplicationFacadeInterface applicationFacade;

	public LoginBean(){
		if(!AppFacade.isDBInitialized()) {
			AppFacade.initializeDB();
		}
		
		applicationFacade = AppFacade.getImpl();
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

	public Boolean getLoggedUser() {
		return null;
	}

	public boolean login(String user, String pass) {		
			return applicationFacade.login(user, pass);
	}

	public String validate() {

		/*boolean login = false;
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
		return login ? "ok" : "error";*/
		return null;
	}

	private void failedValidationMsg(String message, FacesContext fc, String clientId) {
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		fc.addMessage(clientId, msg);
		fc.validationFailed();
		fc.renderResponse();
	}

}
