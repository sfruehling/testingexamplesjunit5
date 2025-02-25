package sf.example.spring.formvalidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FormCustomValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormCustomConstraint {
    String message() default "FormCustomContraint";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
