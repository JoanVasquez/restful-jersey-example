package user.managment.filter;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;

/**
 * @author JoanVasquez
 * Date 19/10/2017
 * A class to configure the filter and setup the token
 */
@NameBinding
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface JWTTokenNeeded {
}