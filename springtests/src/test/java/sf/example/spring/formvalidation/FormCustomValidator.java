package sf.example.spring.formvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FormCustomValidator implements ConstraintValidator<FormCustomConstraint, FormDto> {

    private final MyService myService;


    public FormCustomValidator(MyService myService) {
        this.myService = myService;
    }

    @Override
    public boolean isValid(FormDto formDto, ConstraintValidatorContext constraintValidatorContext) {
        if(myService.callMe())
        {
            return (formDto.firstName.length() + formDto.name.length()) > 6;
        }
        throw new IllegalArgumentException("configure callMe!!!");
    }
}
