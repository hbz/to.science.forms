package services;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import javax.validation.*;

@SuppressWarnings("javadoc")
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = MyDateRangeValidator.class)
@play.data.Form.Display(name = "constraint.validdaterange")
public @interface ValidDateRange {
	String message() default MyDateRangeValidator.message;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
