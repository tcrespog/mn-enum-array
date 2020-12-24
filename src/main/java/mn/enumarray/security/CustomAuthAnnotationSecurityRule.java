package mn.enumarray.security;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.rules.SecuredAnnotationRule;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.rules.SecurityRuleResult;
import io.micronaut.web.router.MethodBasedRouteMatch;
import io.micronaut.web.router.RouteMatch;
import mn.enumarray.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Map;

@Singleton
public class CustomAuthAnnotationSecurityRule implements SecurityRule {

    private static final Logger log = LoggerFactory.getLogger(CustomAuthAnnotationSecurityRule.class);

    @Override
    public int getOrder() {
        return SecuredAnnotationRule.ORDER - 50;
    }

    @Override
    public SecurityRuleResult check(HttpRequest<?> request, @Nullable RouteMatch<?> routeMatch, @Nullable Map<String, Object> claims) {
        if (!(routeMatch instanceof MethodBasedRouteMatch)) {
            return SecurityRuleResult.UNKNOWN;
        }

        MethodBasedRouteMatch methodRoute = (MethodBasedRouteMatch) routeMatch;
        if (!methodRoute.hasAnnotation(CustomAuth.class)) {
            return SecurityRuleResult.UNKNOWN;
        }

        AnnotationValue<CustomAuth> annotation = methodRoute.getAnnotation(CustomAuth.class);
        final Role[] roles = annotation.enumValues("roles", Role.class);

        log.info("The roles: " + Arrays.toString(roles));

        return (roles.length > 0) ? SecurityRuleResult.ALLOWED : SecurityRuleResult.REJECTED;
    }

}
