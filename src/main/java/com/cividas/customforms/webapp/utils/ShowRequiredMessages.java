package com.cividas.customforms.webapp.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.text.MessageFormat;

public class ShowRequiredMessages {

	public void showRequiredMessage (FacesContext context, UIComponent component, Object value, String requiredMessage) {
        if (requiredMessage == null) {
            Object label = component.getAttributes().get("label");
            if (label == null || (label instanceof String && ((String) label).length() == 0)) {
                label = component.getValueExpression("label");
            }
            if (label == null) {
                label = component.getClientId(context);
            }
            requiredMessage = MessageFormat.format(UIInput.REQUIRED_MESSAGE_ID, label);
        }
        
        throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR, requiredMessage, requiredMessage));
	}
}
