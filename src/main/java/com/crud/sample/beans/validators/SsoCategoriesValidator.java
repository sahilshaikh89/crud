package com.crud.sample.beans.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.crud.sample.beans.SsoCategories;

@Component
public class SsoCategoriesValidator implements IValidator<SsoCategories>, Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SsoCategories.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) 
	{
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "category Name should not contain any spaces");
		SsoCategories ssoCategory = (SsoCategories) target;
		
		//Validate if name has any spcaes. This can also be achieved usig @Pattern(regex="")"
		if( ssoCategory.getName().contains(" ") ){
			//INVALID
			errors.rejectValue("name", "error.name.invalid.space", "category Name should not contain any spaces");
		}
	}
}
