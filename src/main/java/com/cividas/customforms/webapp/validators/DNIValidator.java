package com.cividas.customforms.webapp.validators;

import com.ontimize.gui.field.document.NIFDocument;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ResourceBundle;

@FacesValidator("com.cividas.customforms.webapp.validators.dniValidator")
public class DNIValidator implements Validator {

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
//			if(!CIFUtils.isCIFWellFormed(value.toString())) {			
//				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,getResourceMessage(label.toString())+" "+ getResourceMessage("notdnicifvalid"), value + " is not a valid cif;"));
//			}
              
  			if(!NIFDocument.isNIFWellFormed(value.toString()) ) {			
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,label.toString()+" "+ getResourceMessage("notdnivalid"), getResourceMessage(label.toString())+" "+ getResourceMessage("notdnivalid")));
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