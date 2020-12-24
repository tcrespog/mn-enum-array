package mn.enumarray.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import mn.enumarray.enums.Role
import mn.enumarray.security.CustomAuth

import javax.transaction.Transactional

@Controller("/")
@Secured(SecurityRule.IS_ANONYMOUS)
class MainController {

    @Get("/")
    @CustomAuth(roles = [Role.USER, Role.ADMIN])
    HttpResponse get() {
        return HttpResponse.ok()
    }

}
