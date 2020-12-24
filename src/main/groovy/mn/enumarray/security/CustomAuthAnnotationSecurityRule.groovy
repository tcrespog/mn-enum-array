package mn.enumarray.security

import edu.umd.cs.findbugs.annotations.Nullable
import groovy.util.logging.Slf4j
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.http.HttpRequest
import io.micronaut.security.rules.SecuredAnnotationRule
import io.micronaut.security.rules.SecurityRule
import io.micronaut.security.rules.SecurityRuleResult
import io.micronaut.web.router.MethodBasedRouteMatch
import io.micronaut.web.router.RouteMatch
import mn.enumarray.enums.Role

import javax.inject.Singleton

@Slf4j
@Singleton
class CustomAuthAnnotationSecurityRule implements SecurityRule {

    @Override
    int getOrder() {
        return SecuredAnnotationRule.ORDER - 50
    }

    @Override
    SecurityRuleResult check(HttpRequest<?> request, @Nullable RouteMatch<?> routeMatch, @Nullable Map<String, Object> claims) {
        if (routeMatch !instanceof MethodBasedRouteMatch) {
            return SecurityRuleResult.UNKNOWN
        }

        MethodBasedRouteMatch methodRoute = (MethodBasedRouteMatch) routeMatch
        if (!methodRoute.hasAnnotation(CustomAuth)) {
            return SecurityRuleResult.UNKNOWN
        }

        AnnotationValue<CustomAuth> annotation = methodRoute.getAnnotation(CustomAuth)
        Role[] roles = annotation.enumValues('roles', Role)

        log.info("The roles: ${roles}")

        return (roles.length > 0) ? SecurityRuleResult.ALLOWED : SecurityRuleResult.REJECTED
    }

}
