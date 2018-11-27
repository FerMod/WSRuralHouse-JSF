package com.ruralhousejsf.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import businessLogic.AppFacade;
import businessLogic.ApplicationFacadeInterface;
import configuration.Config;
import configuration.ConfigXML;
import domain.RuralHouse;
import exceptions.BadDatesException;
import exceptions.OverlappingOfferException;
import domain.Review.ReviewState;

@FacesValidator("setAvailabilityValidator")
public class SetAvailabilityValidator implements Validator {
	
	private LinkedHashMap<String, Object> ruralHouseHashMap;
	private ApplicationFacadeInterface applicationFacade;

	public SetAvailabilityValidator() {
		Config config = ConfigXML.loadConfig(AppFacade.class.getResource("db/config.xml").getFile());
		applicationFacade = AppFacade.loadConfig(config);
		List<RuralHouse> ruralHouseList = applicationFacade.getRuralHouses(ReviewState.APPROVED);

		ruralHouseHashMap = new LinkedHashMap<String, Object>();
		for (RuralHouse ruralHouse : ruralHouseList) {
			ruralHouseHashMap.put(ruralHouse.getName(), ruralHouse);
		}
	}
	
	public LinkedHashMap<String, Object> getRuralHouseHashMap() {
		return ruralHouseHashMap;
	}

	public void setRuralHouseHashMap(LinkedHashMap<String, Object> ruralHouseHashMap) {
		this.ruralHouseHashMap = ruralHouseHashMap;
	}
	
	public ApplicationFacadeInterface getApplicationFacade() {
		return applicationFacade;
	}

	public void setApplicationFacade(ApplicationFacadeInterface applicationFacade) {
		this.applicationFacade = applicationFacade;
	}
	
	@Override
	public void validate(FacesContext fc, UIComponent component, Object ruralHouse) throws ValidatorException {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat sDF = new SimpleDateFormat(pattern);	
		
		UIInput uiInputStartDate = (UIInput) component.getAttributes().get("startDate");
		UIInput uiInputEndDate = (UIInput) component.getAttributes().get("endDate");
		UIInput uiInputPriceOffer = (UIInput) component.getAttributes().get("priceOffer");
		
		RuralHouse rh = (RuralHouse) getRuralHouseHashMap().get(ruralHouse.toString());
		Date startDate = null;
		Date endDate = null;
		
		try {
			startDate = sDF.parse(uiInputStartDate.getSubmittedValue().toString());
			endDate = sDF.parse(uiInputEndDate.getSubmittedValue().toString());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		double priceOffer = Double.parseDouble(uiInputPriceOffer.getSubmittedValue().toString());
		
		try {
			getApplicationFacade().createOffer(rh, startDate, endDate, priceOffer);
		} catch (OverlappingOfferException e) {
			uiInputStartDate.setValid(false);
			uiInputEndDate.setValid(false);
			throw new ValidatorException(new FacesMessage(
				"La oferta no puede tener fechas coincidentes a otra oferta."));
		} catch (BadDatesException e) {
			uiInputStartDate.setValid(false);
			uiInputEndDate.setValid(false);
			throw new ValidatorException(new FacesMessage(
					"La oferta no puede tener fechas incompatibles."));
		}
		
	}

}
