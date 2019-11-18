package de.hska.iwi.vslab.Api_Product.ConsumingREST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;


public class ConsumeCoreProduct {
    
    private static final Logger log = LoggerFactory.getLogger(ConsumeCoreProduct.class);

    private String urlCoreProduct = "http://localhost:8080/product";
    
    RestTemplate restTemplate = new RestTemplate();

    public Product[] getProducts() {
        Product [] products = restTemplate.getForObject(urlCoreProduct, Product[].class);
        return products;
    }

    public void deleteProduct(int id) {
        restTemplate.delete(urlCoreProduct + "/" + id);
    }

    public void updateProduct(Product product) {
        restTemplate.put(urlCoreProduct, product);
    }

    public void deleteAllProducts() {
        restTemplate.delete(urlCoreProduct);
    }

    public Product getProduct(int id) {
        return restTemplate.getForObject(urlCoreProduct + "/" + id, Product.class);
    }

    public void findProduct(String searchValue, String priceMinValue, String priceMaxValue) {
        restTemplate.getForObject(urlCoreProduct + "/product/find", Product.class, searchValue, priceMinValue, priceMaxValue);
    }

}