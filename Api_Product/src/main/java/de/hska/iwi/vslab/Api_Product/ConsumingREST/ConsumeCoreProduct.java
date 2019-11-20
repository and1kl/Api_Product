package de.hska.iwi.vslab.Api_Product.ConsumingREST;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class ConsumeCoreProduct {

    //private String urlCoreProduct = "http://localhost:8081/product";

    private static final Logger log = LoggerFactory.getLogger(ConsumeCoreProduct.class);
    RestTemplate restTemplate = new RestTemplate();

    public Product[] getProducts() {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getProductURL());
            Product[] products = restTemplate.getForObject(urlBuilder.getProductURL(), Product[].class);
            return products;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void deleteProduct(int id) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getSlashURL());
            restTemplate.delete(urlBuilder.getSlashURL() + id);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void updateProduct(Product product) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getBaseUrl_core_product());
            restTemplate.put(urlBuilder.getBaseUrl_core_product(), product);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void deleteAllProducts() {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getBaseUrl_core_product());
            restTemplate.delete(urlBuilder.getBaseUrl_core_product());
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public Product getProduct(int id) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getBaseUrl_core_product());
            return restTemplate.getForObject(urlBuilder.getUrlWithId(id), Product.class);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public Product[] findProduct(Optional<String> searchValue, Optional<String> priceMinValue,Optional<String> priceMaxValue) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            String url = urlBuilder.getFilterUrl(searchValue, priceMinValue,priceMaxValue);
            log.info("URL:" + url);
            Product[] list = restTemplate.getForObject(url, Product[].class);
            return list;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

}