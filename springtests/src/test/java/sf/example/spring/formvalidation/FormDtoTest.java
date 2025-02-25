package sf.example.spring.formvalidation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FormDtoTest {

    MyService myService = mock(MyService.class);
    // Create the custom validator with mocked dependencies
    FormCustomValidator customValidator = new FormCustomValidator(myService);


    Validator validator;

    @BeforeEach
    void setUp() {
        // Create a custom ConstraintValidatorFactory that returns our custom validator
        ConstraintValidatorFactory myConstraintValidatorFactory = new ConstraintValidatorFactory() {
            @Override
            public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
                if (key.isAssignableFrom(FormCustomValidator.class)) {
                    return (T) customValidator;
                }
                try {
                    return key.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Could not instantiate validator: " + key, e);
                }
            }

            @Override
            public void releaseInstance(ConstraintValidator<?, ?> instance) {
                // No-op
            }
        };

        // Create a ValidatorFactory with our custom factory
        ValidatorFactory validationFactory = Validation.byDefaultProvider()
                .configure()
                .constraintValidatorFactory(myConstraintValidatorFactory)
                .buildValidatorFactory();

        validator = validationFactory.getValidator();
    }

    @Test
    void validateDto() {
        when(myService.callMe()).thenReturn(true);
        FormDto formDto = new FormDto("name", "firstname");
        Set<ConstraintViolation<FormDto>> constraintViolations = validator.validate(formDto);
        assertThat(constraintViolations).isEmpty();
    }
}
