package com.ruralhousejsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import org.omnifaces.converter.SelectItemsConverter;

import com.ruralhousejsf.domain.RuralHouse;

@FacesConverter("ruralHouseConverter")
public class RuralHouseConverter extends SelectItemsConverter  {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {		
		Long id = (value instanceof RuralHouse) ? ((RuralHouse) value).getId() : null;
		return (id != null) ? String.valueOf(id) : null;
	}

}
