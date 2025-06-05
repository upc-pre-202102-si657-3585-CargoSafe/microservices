package  com.dynoware.cargosafe.iamservice.iam.infrastructure.tokens.jwt;

import com.dynoware.cargosafe.iamservice.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface BearerTokenService extends TokenService {
    String getBearerTokenFrom(HttpServletRequest request);
    String generateToken(Authentication authentication);
}
