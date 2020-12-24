package mn.enumarray.security;

import mn.enumarray.enums.Role;

public @interface CustomAuth {

    Role[] roles();

}
