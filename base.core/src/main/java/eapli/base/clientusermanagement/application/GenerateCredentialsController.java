package eapli.base.clientusermanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BasePasswordPolicy;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class GenerateCredentialsController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserRepository userRepository = PersistenceContext.repositories().users();

    public List<SystemUser> getUsers(){
        authz.isAuthenticatedUserAuthorizedTo(BaseRoles.SALES_CLERK);

        Iterable<SystemUser> iterable = userRepository.findAll();
        Iterator<SystemUser> iterator = iterable.iterator();
        SystemUser systemUser;
        List<SystemUser> list = new ArrayList<>();
        while(iterator.hasNext()){
            systemUser = iterator.next();
            if(systemUser.passwordMatches("DEFAULT1", new PlainTextEncoder())){
                list.add(systemUser);
            }
        }
        return list;
    }

    public void generate(SystemUser systemUser) {
        String pwd = RandomStringUtils.randomAlphanumeric( 10);
        Optional<Password> password = Password.encodedAndValid(pwd, new BasePasswordPolicy(), new PlainTextEncoder());
        systemUser.changePassword(password.get());
        userRepository.save(systemUser);
    }
}
