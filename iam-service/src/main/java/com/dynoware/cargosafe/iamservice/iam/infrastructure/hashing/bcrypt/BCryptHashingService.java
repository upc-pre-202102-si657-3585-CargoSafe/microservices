package  com.dynoware.cargosafe.iamservice.iam.infrastructure.hashing.bcrypt;

import com.dynoware.cargosafe.iamservice.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
