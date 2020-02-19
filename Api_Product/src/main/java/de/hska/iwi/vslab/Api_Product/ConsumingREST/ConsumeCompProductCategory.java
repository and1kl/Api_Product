package de.hska.iwi.vslab.Api_Product.ConsumingREST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ConsumeCompProductCategory {

    // private String urlCoreProduct =
    // "http://localhost:8081//comp_product_category/product";

    private static final Logger log = LoggerFactory.getLogger(ConsumeCoreProduct.class);
    RestTemplate restTemplate = new RestTemplate();

    public OAuth2ProtectedResourceDetails oAuth2ResourceDetails() {
        //BaseOAuth2ProtectedResourceDetails details = new BaseOAuth2ProtectedResourceDetails();
        //ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();

        details.setClientId("compProductCategoryId");
        details.setClientSecret("compProductCategorySecret");
        details.setAccessTokenUri("http://oauthserver:8094/oauth/token");
        //details.setGrantType("client_credentials");
        List<String> scope = new ArrayList<>();
        scope.add("read");scope.add("write");
        details.setScope(scope);
        details.setAuthenticationScheme(AuthenticationScheme.header);
        details.setClientAuthenticationScheme(AuthenticationScheme.header);
        details.setId("1");
        details.setTokenName("Comp_Product_Category");

        //log.info("OAUTH DETAILS" + clientId + clientSecret + details.getAccessTokenUri() + details.getGrantType());
        //details.set
        return details;
    }

    @Bean // has to be done at runtime because the authorization server would not be up otherwise
    public OAuth2RestTemplate foo() {
        //OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(oAuth2ResourceDetails());
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        return new OAuth2RestTemplate(oAuth2ResourceDetails(), new DefaultOAuth2ClientContext(atr));
        /* To validate if required configurations are in place during startup */
        //oAuth2RestTemplate.getAccessToken();
        //return oAuth2RestTemplate;
    }


    public void addProduct(Product payload) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getAddProductURL());
            HttpEntity<Product> request = new HttpEntity<>(
                    new Product(payload.getName(), payload.getPrice(), payload.getCategoryId(), payload.getDetails()));
            OAuth2RestTemplate restTemplate3 = foo();
            restTemplate3.postForLocation(urlBuilder.getAddProductURL(), request);
            //restTemplate.postForLocation(urlBuilder.getAddProductURL(), request);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

}