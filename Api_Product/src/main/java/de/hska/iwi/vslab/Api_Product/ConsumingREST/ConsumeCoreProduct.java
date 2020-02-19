package de.hska.iwi.vslab.Api_Product.ConsumingREST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.web.client.RestTemplate;

public class ConsumeCoreProduct {

    // private String urlCoreProduct = "http://localhost:8081/product";

    private static final Logger log = LoggerFactory.getLogger(ConsumeCoreProduct.class);
    RestTemplate restTemplate = new RestTemplate();

    public OAuth2ProtectedResourceDetails oAuth2ResourceDetails() {
        //BaseOAuth2ProtectedResourceDetails details = new BaseOAuth2ProtectedResourceDetails();
        //ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();

        details.setClientId("coreProductId");
        details.setClientSecret("coreProductSecret");
        details.setAccessTokenUri("http://oauthserver:8094/oauth/token");
        //details.setGrantType("client_credentials");
        List<String> scope = new ArrayList<>();
        scope.add("read");scope.add("write");
        details.setScope(scope);
        details.setAuthenticationScheme(AuthenticationScheme.header);
        details.setClientAuthenticationScheme(AuthenticationScheme.header);
        details.setId("1");
        details.setTokenName("Core_Product");

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

    public Product[] getProducts() {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getProductURL());
            OAuth2RestTemplate restTemplate3 = foo();
            Product[] products = restTemplate3.getForObject(urlBuilder.getProductURL(), Product[].class);
            //Product[] products = restTemplate.getForObject(urlBuilder.getProductURL(), Product[].class);
            return products;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void deleteProduct(int id) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getUrlWithId(id));
            OAuth2RestTemplate restTemplate3 = foo();
            restTemplate3.delete(urlBuilder.getUrlWithId(id));
            //restTemplate.delete(urlBuilder.getUrlWithId(id));
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void updateProduct(Product product) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getUrlWithId(product.getId()));
            OAuth2RestTemplate restTemplate3 = foo();
            restTemplate3.put(urlBuilder.getUrlWithId(product.getId()), product);
            //restTemplate.put(urlBuilder.getUrlWithId(product.getId()), product);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void deleteAllProducts() {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getProductURL());
            OAuth2RestTemplate restTemplate3 = foo();
            restTemplate3.delete(urlBuilder.getProductURL());
            //restTemplate.delete(urlBuilder.getProductURL());
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public Product getProduct(int id) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getUrlWithId(id));
            OAuth2RestTemplate restTemplate3 = foo();
            return restTemplate3.getForObject(urlBuilder.getUrlWithId(id), Product.class);
            //return restTemplate.getForObject(urlBuilder.getUrlWithId(id), Product.class);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public Product[] findProduct(Optional<String> searchValue, Optional<String> priceMinValue,
            Optional<String> priceMaxValue) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            String url = urlBuilder.getFilterUrl(searchValue, priceMinValue, priceMaxValue);
            log.info("URL:" + url);
            OAuth2RestTemplate restTemplate3 = foo();
            Product[] list = restTemplate3.getForObject(url, Product[].class);
            //Product[] list = restTemplate.getForObject(url, Product[].class);
            return list;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

}