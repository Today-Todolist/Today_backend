package todolist.today.today.global.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import todolist.today.today.global.security.auth.AuthDetails;

@Component
public class AuthenticationFacade {

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUserId() {
        return ((AuthDetails)getAuthentication().getPrincipal()).getId();
    }

}
