package ekaterina.pojo.validator;

import ekaterina.pojo.Sensor;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SensorValidator implements Validator {
	@Override
	public boolean supports(Class<?> aClass) {
		return Validator.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Sensor sensor = (Sensor)o;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
	}
}
