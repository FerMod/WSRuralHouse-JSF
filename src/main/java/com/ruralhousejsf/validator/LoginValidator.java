package com.ruralhousejsf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("loginValidator")
public class LoginValidator implements Validator {

	public LoginValidator() {
		super();
	}

	@Override
	public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
		//UIInput uiInputUser = (UIInput) fc.getAttributes().get("login:username");
		//UIInput uiInputPass = (UIInput) fc.getAttributes().get("login:password");

		String user = value.toString();
		//String pass = uiInputPass.getSubmittedValue().toString();

		if (user.equals("Prueba")) {
			//uiInputUser.setValid(false);
			throw new ValidatorException(new FacesMessage(
					"Esta validación ha saltado."));
		}

	}

}
