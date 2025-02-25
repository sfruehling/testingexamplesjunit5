package sf.example.spring.formvalidation;

import javax.validation.constraints.NotBlank;

@FormCustomConstraint
public class FormDto {

    @NotBlank
    String name;

    @NotBlank
    String firstName;

    public FormDto(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
    }
}
