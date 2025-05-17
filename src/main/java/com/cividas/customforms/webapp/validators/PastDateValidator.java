package com.cividas.customforms.webapp.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

@FacesValidator("com.cividas.customforms.webapp.validators.pastDateValidator")
public class PastDateValidator implements Validator {

	ResourceBundle i18n;
	
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value!=null && !value.toString().isEmpty()) {
			  Object label = component.getAttributes().get("label");
              if (label == null || (label instanceof String && ((String) label).length() == 0)) {
                  label = component.getValueExpression("label");
              }
              if (label == null) {
                  label = component.getClientId(context);
              }

              if(value instanceof Date) {
            	  Date datevalue= Calendar.getInstance().getTime();
            	  if(datevalue.compareTo((Date)value)<0) {
            		  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,label.toString()+" "+ getResourceMessage("datemustbebeforetoday"), label.toString()+" "+ getResourceMessage("datemustbebeforetoday")));
            	  }
              }

		}
	}

	
	protected String getResourceMessage(String key) {
		String message = key;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			i18n = fc.getApplication().evaluateExpressionGet(fc, "#{i18n}", ResourceBundle.class);
			message = i18n.getString(key);
		} catch (Exception e) {			
//			e.printStackTrace();
			message = i18n.getString("NO_VALIDATION_DONE");
		}
		return message;
	}
}