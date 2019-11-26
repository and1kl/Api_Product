package de.hska.iwi.vslab.Api_Product.ConsumingREST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public class ConsumeCompProductCategory {

    // private String urlCoreProduct =
    // "http://localhost:8081//comp_product_category/product";

    private static final Logger log = LoggerFactory.getLogger(ConsumeCoreProduct.class);
    RestTemplate restTemplate = new RestTemplate();

    public void addProduct(Product payload) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getAddProductURL());
            HttpEntity<Product> request = new HttpEntity<>(
                    new Product(payload.getName(), payload.getPrice(), payload.getCategoryId(), payload.getDetails()));
            restTemplate.postForLocation(urlBuilder.getAddProductURL(), request);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

}