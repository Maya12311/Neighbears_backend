package com.example.neighbears.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationEvents {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationEvents.class);


    @EventListener
    public void onFailure(AuthorizationDeniedEvent deniedEvent){
        logger.error("Authorization failed for the user: {} due to: {}", deniedEvent.getAuthentication().get().getName(),
           deniedEvent.getAuthorizationDecision().toString());
    }

}
