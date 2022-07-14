package com.auspost.location.api.security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
public class ApiAuthMngr implements AuthenticationManager{

   private String principalRequestValue;

   public ApiAuthMngr(String principalRequestValue){
       this.principalRequestValue=principalRequestValue;
   }
  
   

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = (String) authentication.getPrincipal();
        if (!principalRequestValue.equals(principal))
        {
            throw new BadCredentialsException("The API key was not found or not the expected value.");
        }
        authentication.setAuthenticated(true);
        return authentication;
    }
    
}
