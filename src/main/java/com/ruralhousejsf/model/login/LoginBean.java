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
		applicationFacade = AppFacade.getImpl(true);
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

		boolean login = login(user, pass);
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if (login(user, pass)) {
			return "ok";
		} else {
			failedValidationMsg("El nombre de usuario o la contraseña no son correctos.", fc, fc.getCurrentPhaseId().toString());
			return "error";
		}
	}

	private void failedValidationMsg(String message, FacesContext fc, String clientId) {
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		fc.addMessage(clientId, msg);
		fc.validationFailed();
		fc.renderResponse();
	}

}
