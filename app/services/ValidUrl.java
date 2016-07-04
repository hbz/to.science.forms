package services;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import javax.validation.*;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = MyUrlValidator.class)
@play.data.Form.Display(name = "constraint.validurl")
public @interface ValidUrl {
	String message() default MyUrlValidator.message;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
