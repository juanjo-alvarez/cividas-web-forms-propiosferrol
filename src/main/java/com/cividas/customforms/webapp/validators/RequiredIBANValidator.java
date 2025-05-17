package com.cividas.customforms.webapp.validators;

import com.cividas.customforms.webapp.utils.ShowRequiredMessages;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.math.BigInteger;

/**
 * Comprueba que el numero de cuenta ha sido indicado y si el formato 
 * de este es correcto
 * @author yasmin.lago
 *
 */

@FacesValidator("com.cividas.customforms.webapp.validators.RequiredIBANValidator")
public class RequiredIBANValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object iban) throws ValidatorException {

		String requiredMessage = ((UIInput) component).getRequiredMessage();
		
		//Si han indicado un numero de cuenta comprobamos su formato
		if ((String)iban != null) {
			
			boolean isValid = false;
			int i = 2;
			int caracterASCII = 0;
			int resto = 0;
			int dc = 0;
			String cadenaDc = "";
			BigInteger cuentaNumero = new BigInteger("0");
			BigInteger modo = new BigInteger("97");
			
			if (((String) iban).length() == 24 && ((String) iban).substring(0, 1).toUpperCase().equals("E")
					&& ((String) iban).substring(1, 2).toUpperCase().equals("S")) {
	
				do {
					caracterASCII = ((String) iban).codePointAt(i);
					isValid = (caracterASCII > 47 && caracterASCII < 58);
					i++;
				} while (i < ((String) iban).length() && isValid);
	
				if (isValid) {
					cuentaNumero = new BigInteger(((String) iban).substring(4, 24) + "142800");
					resto = cuentaNumero.mod(modo).intValue();
					dc = 98 - resto;
					cadenaDc = String.valueOf(dc);
				}
	
				if (dc < 10) {
					cadenaDc = "0" + cadenaDc;
				}
	
				// Comparamos los caracteres 2 y 3 de la cuenta (dígito de control IBAN) con
				// cadenaDc
				if (((String) iban).substring(2, 4).equals(cadenaDc)) {
					isValid = true;
				} else {
					isValid = false;
		            ShowRequiredMessages messages = new ShowRequiredMessages();
		            messages.showRequiredMessage(context, component, iban, requiredMessage);
				}
			} 		
		} else {
            ShowRequiredMessages messages = new ShowRequiredMessages();
            messages.showRequiredMessage(context, component, iban, requiredMessage);
		}
	}

}
