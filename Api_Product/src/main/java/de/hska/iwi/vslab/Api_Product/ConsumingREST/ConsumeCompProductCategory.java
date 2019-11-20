package de.hska.iwi.vslab.Api_Product.ConsumingREST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class ConsumeCompProductCategory {

    //private String urlCoreProduct = "http://localhost:8081//comp_product_category/product";

    private static final Logger log = LoggerFactory.getLogger(ConsumeCoreProduct.class);
    RestTemplate restTemplate = new RestTemplate();

    public void addProduct(String name, double price, int categoryId, String details) {
        try {
        UrlBuilder urlBuilder = new UrlBuilder();
        log.info("URL:" + urlBuilder.getBaseUrl());
        restTemplate.postForLocation(urlBuilder.getAddProductURL(), name, price, categoryId, details);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

}