/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "EmailValidator")
public class EmailValidator implements Validator {

    public static final String EMAIL_VALIDATOR = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        Pattern pattern = Pattern.compile(EMAIL_VALIDATOR);
        String correo = ((String) value).trim();
        if (correo.isEmpty()) {  
        }
        else{
            Matcher matcher = pattern.matcher(correo);
            if(!matcher.matches()){
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error",
                    value + " el correo no es válido");
                throw new ValidatorException(msg);
            }
        }
    }

}
