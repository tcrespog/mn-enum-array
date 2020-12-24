package mn.enumarray.security

import mn.enumarray.enums.Role

@interface CustomAuth {

    Role[] roles()

}