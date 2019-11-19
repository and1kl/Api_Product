package de.hska.iwi.vslab.Api_Product.ConsumingREST;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import de.hska.iwi.vslab.Api_Product.Controllers.ApiProductController;
import java.io.IOException;
import org.springframework.web.client.RestClientException;

public class ConsumeCoreProduct {

    private static final Logger log = LoggerFactory.getLogger(ConsumeCoreProduct.class);

    private String urlCoreProduct = "http://localhost:8080/product";

    RestTemplate restTemplate = new RestTemplate();

    public Product[] getProducts(String baseUrl) {
        try {
            log.info("baseUrl test:" + baseUrl);
            Product[] products = restTemplate.getForObject(baseUrl, Product[].class);
            return products;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
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

    public Product[] findProduct(Optional<String> searchValue, Optional<String> priceMinValue,
            Optional<String> priceMaxValue) {
        Product[] list = restTemplate.getForObject(urlCoreProduct + "/product/find", Product[].class, searchValue,
                priceMinValue, priceMaxValue);
        return list;
    }

}