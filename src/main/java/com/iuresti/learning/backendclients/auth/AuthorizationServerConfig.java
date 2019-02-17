package com.iuresti.learning.backendclients.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.iuresti.learning.backendclients.services.FirebaseService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private FirebaseAuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    private FirebaseService firebaseService;

    private AditionalJWTInformation aditionalJWTInformation;

    @Autowired
    private ClientDetailsService clientDetailsService;

    public AuthorizationServerConfig(FirebaseAuthenticationManager authenticationManager,
                                     PasswordEncoder passwordEncoder,
                                     AditionalJWTInformation aditionalJWTInformation,
                                     FirebaseService firebaseService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.aditionalJWTInformation = aditionalJWTInformation;
        this.firebaseService = firebaseService;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("angular-app")
                .secret(passwordEncoder.encode("12345"))
                .scopes("read", "write")
                .authorizedGrantTypes("firebase", "refresh_token")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600);
    }

    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory() {
        DefaultOAuth2RequestFactory defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
        defaultOAuth2RequestFactory.setCheckUserScopes(true);

        return defaultOAuth2RequestFactory;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(aditionalJWTInformation, accessTokenConverter()));

        FirebaseTokenGranter firebaseTokenGranter = new FirebaseTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), firebaseService, authenticationManager);

        endpoints.accessTokenConverter(accessTokenConverter())
                .tokenGranter(firebaseTokenGranter)
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();

        accessTokenConverter.setSigningKey(JwtConfig.PRIVATE_KEY);
        accessTokenConverter.setVerifierKey(JwtConfig.PUBLIC_KEY);

        return accessTokenConverter;
    }
}
